package cphbusiness.noinputs.main.controller;

import cphbusiness.noinputs.main.dto.FoodItemDTO;
import cphbusiness.noinputs.main.dto.RestaurantDTO;
import cphbusiness.noinputs.main.exception.RestaurantNotFoundException;
import cphbusiness.noinputs.main.service.RestaurantService;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestaurantController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RestaurantControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void testGetAllRestaurants() throws Exception {
        Faker faker = new Faker();
        List<RestaurantDTO> restaurantDTOList = new ArrayList<>();
        restaurantDTOList.add(new RestaurantDTO(1L, faker.restaurant().name()));
        restaurantDTOList.add(new RestaurantDTO(2L,faker.restaurant().name()));
        restaurantDTOList.add(new RestaurantDTO(3L,faker.restaurant().name()));

        when(restaurantService.getAllRestaurants()).thenReturn(restaurantDTOList);

        this.mockMvc.perform(get("/restaurants").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\":\""+ restaurantDTOList.get(0).getName() +"\"},{\"name\":\""+ restaurantDTOList.get(1).getName() +"\"},{\"name\":\""+ restaurantDTOList.get(2).getName() +"\"}]"));
    }

    // TODO: Get menu aswell
    @Test
    public void getRestaurant() throws Exception {
        // Using datafaker to generate random data
        Faker faker = new Faker();

        // Creating a list of food items
        List<FoodItemDTO> foodItems = new ArrayList<>();

        //Generating random dish name and price
        String foodItemNameOne = faker.food().dish();
        Long priceOne = new Random().nextLong(10, 200);
        String foodItemNameTwo = faker.food().dish();
        Long priceTwo = new Random().nextLong(10, 200);

        // Adding food items to list
        foodItems.add(new FoodItemDTO(1L, foodItemNameOne, priceOne));
        foodItems.add(new FoodItemDTO(2L, foodItemNameTwo, priceTwo));

        // Creating a restaurantDTO
        RestaurantDTO restaurantDTO = new RestaurantDTO(2L,faker.restaurant().name(), foodItems);

        // Mocking the restaurantService
        when(restaurantService.getRestaurant(2L)).thenReturn(restaurantDTO);

        // Testing the getRestaurant method
        this.mockMvc.perform(get("/restaurants/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\":\""+ restaurantDTO.getName() +"\"}"))
                .andExpect(content().json("{\"menu\": [{ \"name\": \""+ foodItemNameOne +"\", \"price\": "+ priceOne + " }, {\"name\": \""+ foodItemNameTwo +"\", \"price\": "+ priceTwo + " }] }"));
    }

    @Test
    public void getRestaurantNotFoundShouldReturn404() throws Exception {
        when(restaurantService.getRestaurant(2732L)).thenThrow(RestaurantNotFoundException.class);

        this.mockMvc.perform(get("/restaurants/2732").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getRestaurantBadRequestShouldReturn400() throws Exception {
        this.mockMvc.perform(get("/restaurants/a").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
