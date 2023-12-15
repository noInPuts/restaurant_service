package cphbusiness.noinputs.main.unit.repository;

import cphbusiness.noinputs.main.model.FoodItem;
import cphbusiness.noinputs.main.model.Restaurant;
import cphbusiness.noinputs.main.repository.RestaurantRepository;
import cphbusiness.noinputs.main.service.DataInitializerService;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RestaurantRepositoryTests {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @MockBean
    private DataInitializerService dataInitializerService;

    @Test
    public void findAll() {
        // Arrange
        Faker faker = new Faker();
        List<String> restaurantNames = new ArrayList<>();

        for(long i = 1L; i<4L; i++) {
            String restaurantName = faker.restaurant().name();
            restaurantNames.add(restaurantName);
            restaurantRepository.save(new Restaurant(i, restaurantName, faker.address().fullAddress(), faker.phoneNumber().cellPhone(), faker.internet().emailAddress()));
        }

        // Act
        List<Restaurant> restaurants = restaurantRepository.findAll();

        // Assert
        assertEquals(3, restaurants.size());
        for(String restaurantName : restaurantNames) {
            assertTrue(restaurants.stream().anyMatch(restaurant -> restaurant.getName().equals(restaurantName)));
        }
    }

    @Test
    public void findById() {
        // Arrange
        // Using datafaker to generate random data
        Faker faker = new Faker();

        // Generating random restaurant name and menu
        String restaurantName = faker.restaurant().name();
        String foodItemNameOne = faker.food().dish();
        String foodItemNameTwo = faker.food().dish();
        String foodItemNameThree = faker.food().dish();
        long priceOne = new Random().nextLong(10, 200);
        long priceTwo = new Random().nextLong(10, 200);
        long priceThree = new Random().nextLong(10, 200);

        // Creating a list of food items
        List<FoodItem> menu = new ArrayList<>();

        // Creating a restaurant with the generated name and menu
        restaurantRepository.save(new Restaurant(restaurantName, faker.address().fullAddress(), faker.phoneNumber().cellPhone(), faker.internet().emailAddress()));
        restaurantRepository.findById(1L).ifPresent(restaurant -> {

            // Adding food items to the menu
            menu.add(new FoodItem(foodItemNameOne, priceOne, restaurant, 1L));
            menu.add(new FoodItem(foodItemNameTwo, priceTwo, restaurant, 2L));
            menu.add(new FoodItem(foodItemNameThree, priceThree, restaurant, 3L));
            restaurant.setMenu(menu);

            restaurantRepository.save(restaurant);
        });

        // Act
        // Getting the restaurant from the database
        Restaurant restaurant = restaurantRepository.findById(1L).orElse(null);

        // Assert
        assertNotNull(restaurant);
        assertEquals(restaurantName, restaurant.getName());
        assertEquals(menu.size(), restaurant.getMenu().size());
        assertEquals(foodItemNameOne, restaurant.getMenu().get(0).getName());
        assertEquals(priceOne, restaurant.getMenu().get(0).getPrice());
        assertEquals(foodItemNameTwo, restaurant.getMenu().get(1).getName());
        assertEquals(priceTwo, restaurant.getMenu().get(1).getPrice());
        assertEquals(foodItemNameThree, restaurant.getMenu().get(2).getName());
        assertEquals(priceThree, restaurant.getMenu().get(2).getPrice());

    }
}
