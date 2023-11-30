package cphbusiness.noinputs.main.controller;

import cphbusiness.noinputs.main.model.FoodItem;
import cphbusiness.noinputs.main.model.Restaurant;
import cphbusiness.noinputs.main.repository.RestaurantRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RestaurantIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    public void getAllRestaurants() throws Exception {
        List<String> restaurantNames = persistDummyRestaurants();

        this.mockMvc.perform(get("/restaurants").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\":\""+ restaurantNames.get(0) +"\"},{\"name\":\""+ restaurantNames.get(1) +"\"},{\"name\":\""+ restaurantNames.get(2) +"\"}]"));
    }

    @Test
    public void getRestaurant() throws Exception {
        Faker faker = new Faker();
        List<String> restaurantNames = persistDummyRestaurants();
        List<String> foodItemNames = new ArrayList<>();
        List<Long> foodPrices = new ArrayList<>();
        Random random = new Random();

        for (String restaurantName: restaurantNames) {
            foodItemNames.add(faker.food().dish());
            foodPrices.add(random.nextLong(2, 200));
        }

        restaurantRepository.findById(2L).ifPresent(
                restaurant -> {
                    List<FoodItem> menu = new ArrayList<>();
                    menu.add(new FoodItem(foodItemNames.get(0), foodPrices.get(0), restaurant));
                    menu.add(new FoodItem(foodItemNames.get(1), foodPrices.get(1), restaurant));
                    menu.add(new FoodItem(foodItemNames.get(2), foodPrices.get(2), restaurant));
                    restaurant.setMenu(menu);

                    restaurantRepository.save(restaurant);
                }
        );

        this.mockMvc.perform(get("/restaurants/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\":\""+ restaurantNames.get(1) +"\", \"menu\": [" +
                        "{\"name\": \""+ foodItemNames.get(0) +"\", \"price\": "+ foodPrices.get(0) +"}," +
                        "{\"name\": \""+ foodItemNames.get(1) +"\", \"price\": "+ foodPrices.get(1) +"}," +
                        "{\"name\": \""+ foodItemNames.get(2) +"\", \"price\": "+ foodPrices.get(2) +"}] }"));
    }

    @Test
    public void getRestaurantNotFoundShouldReturn404() throws Exception {
        this.mockMvc.perform(get("/restaurants/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getRestaurantBadRequestShouldReturn400() throws Exception {
        this.mockMvc.perform(get("/restaurants/abc").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private List<String> persistDummyRestaurants() {
        // Using datafaker to generate random data
        Faker faker = new Faker();

        List<String> restaurantNames = new ArrayList<>();

        for(long i = 1L; i<4L; i++) {
            String restaurantName = faker.restaurant().name();
            restaurantNames.add(restaurantName);
            restaurantRepository.save(new Restaurant(i, restaurantName));
        }

        return restaurantNames;
    }
}
