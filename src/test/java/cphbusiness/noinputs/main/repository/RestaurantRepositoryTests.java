package cphbusiness.noinputs.main.repository;

import cphbusiness.noinputs.main.model.FoodItem;
import cphbusiness.noinputs.main.model.Restaurant;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RestaurantRepositoryTests {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    public void findAll() {
        Faker faker = new Faker();
        List<String> restaurantNames = new ArrayList<>();

        for(long i = 1L; i<4L; i++) {
            String restaurantName = faker.restaurant().name();
            restaurantNames.add(restaurantName);
            restaurantRepository.save(new Restaurant(i, restaurantName));
        }

        List<Restaurant> restaurants = restaurantRepository.findAll();

        assertEquals(3, restaurants.size());
        for(String restaurantName : restaurantNames) {
            assertTrue(restaurants.stream().anyMatch(restaurant -> restaurant.getName().equals(restaurantName)));
        }
    }

    @Test
    public void findById() {
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
        restaurantRepository.save(new Restaurant(restaurantName));
        restaurantRepository.findById(1L).ifPresent(restaurant -> {

            // Adding food items to the menu
            menu.add(new FoodItem(foodItemNameOne, priceOne, restaurant));
            menu.add(new FoodItem(foodItemNameTwo, priceTwo, restaurant));
            menu.add(new FoodItem(foodItemNameThree, priceThree, restaurant));
            restaurant.setMenu(menu);

            restaurantRepository.save(restaurant);
        });

        // Getting the restaurant from the database
        Restaurant restaurant = restaurantRepository.findById(1L).orElse(null);

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
