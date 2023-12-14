package cphbusiness.noinputs.main.unit.facade;

import cphbusiness.noinputs.main.dto.RestaurantDTO;
import cphbusiness.noinputs.main.exception.RestaurantNotFoundException;
import cphbusiness.noinputs.main.facade.ServiceFacade;
import cphbusiness.noinputs.main.service.RestaurantService;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ServiceFacadeTests {

    @Autowired
    private ServiceFacade serviceFacade;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void getAllRestaurantsTest() {
        // Mocking restaurantService
        Faker faker = new Faker();
        List<RestaurantDTO> mockedRestaurants = new ArrayList<>();
        mockedRestaurants.add(new RestaurantDTO(1L, faker.restaurant().name(), faker.address().fullAddress(), faker.phoneNumber().cellPhone(), faker.internet().emailAddress()));
        mockedRestaurants.add(new RestaurantDTO(2L, faker.restaurant().name(), faker.address().fullAddress(), faker.phoneNumber().cellPhone(), faker.internet().emailAddress()));
        mockedRestaurants.add(new RestaurantDTO(3L, faker.restaurant().name(), faker.address().fullAddress(), faker.phoneNumber().cellPhone(), faker.internet().emailAddress()));
        when(restaurantService.getAllRestaurants()).thenReturn(mockedRestaurants);

        // Getting all the restaurants
        List<RestaurantDTO> restaurants = serviceFacade.getAllRestaurants();

        // Asserting that the list is not empty
        assertFalse(restaurants.isEmpty());
        assertEquals(3, restaurants.size());
        assertEquals(mockedRestaurants.get(0).getName(), restaurants.get(0).getName());
        assertEquals(mockedRestaurants.get(1).getName(), restaurants.get(1).getName());
        assertEquals(mockedRestaurants.get(2).getName(), restaurants.get(2).getName());
    }

    @Test
    public void getRestaurantTest() throws RestaurantNotFoundException {
        // Mocking restaurantService
        Faker faker = new Faker();
        RestaurantDTO mockedRestaurant = new RestaurantDTO(1L, faker.restaurant().name(), faker.address().fullAddress(), faker.phoneNumber().cellPhone(), faker.internet().emailAddress());
        when(restaurantService.getRestaurant(1L)).thenReturn(mockedRestaurant);

        // Getting the restaurant
        RestaurantDTO restaurant = serviceFacade.getRestaurant(1L);

        // Asserting that the restaurant is not null
        assertEquals(mockedRestaurant.getName(), restaurant.getName());
    }
}
