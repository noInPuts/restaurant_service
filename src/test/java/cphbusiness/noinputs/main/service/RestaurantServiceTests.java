package cphbusiness.noinputs.main.service;

import cphbusiness.noinputs.main.dto.RestaurantDTO;
import cphbusiness.noinputs.main.exception.RestaurantNotFoundException;
import cphbusiness.noinputs.main.model.FoodItem;
import cphbusiness.noinputs.main.model.Restaurant;
import cphbusiness.noinputs.main.repository.RestaurantRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RestaurantServiceTests {

    @Autowired
    private RestaurantService restaurantService;

    @MockBean
    private RestaurantRepository restaurantRepository;

    @Test
    public void getAllRestaurants() {
        Faker faker = new Faker();
        List<String> restaurantNames = new ArrayList<>();

        for(long i = 1L; i<4L; i++) {
            restaurantNames.add(faker.restaurant().name());
        }

        when(restaurantRepository.findAll()).thenReturn(
                List.of(
                        new Restaurant(1L, restaurantNames.get(0)),
                        new Restaurant(2L, restaurantNames.get(1)),
                        new Restaurant(3L, restaurantNames.get(2))
                )
        );
        List<RestaurantDTO> restaurants = restaurantService.getAllRestaurants();

        assertEquals(3, restaurants.size());
        assertEquals(restaurantNames.get(0), restaurants.get(0).getName());
        assertEquals(restaurantNames.get(1), restaurants.get(1).getName());
        assertEquals(restaurantNames.get(2), restaurants.get(2).getName());
    }

    @Test
    public void getRestaurant() throws RestaurantNotFoundException {
        // Using datafaker to generate random data
        Faker faker = new Faker();

        // Generating random restaurant name
        String restaurantName = faker.restaurant().name();

        // Creating a list of food items
        List<FoodItem> foodItems = new ArrayList<>();
        foodItems.add(new FoodItem(1L, faker.food().dish(), new Random().nextLong(20, 200)));

        // Mocking the restaurant repository
        when(restaurantRepository.findById(2L)).thenReturn(
                java.util.Optional.of(new Restaurant(2L, restaurantName, foodItems))
        );

        // Getting restaurant from service
        RestaurantDTO restaurant = restaurantService.getRestaurant(2L);

        assertEquals(restaurantName, restaurant.getName());
        assertEquals(1, restaurant.getMenu().size());
        assertEquals(foodItems.get(0).getName(), restaurant.getMenu().get(0).getName());
        assertEquals(foodItems.get(0).getPrice(), restaurant.getMenu().get(0).getPrice());
    }

    @Test
    public void getRestaurantNotFoundShouldThrowException() {
        when(restaurantRepository.findById(2732L)).thenReturn(
                java.util.Optional.empty()
        );

        assertThrows(RestaurantNotFoundException.class, () -> {
            restaurantService.getRestaurant(2732L);
        });

        try {
            restaurantService.getRestaurant(2732L);
        } catch (RestaurantNotFoundException e) {
            assertEquals("Restaurant with id 2732 not found", e.getMessage());
        }
    }
}
