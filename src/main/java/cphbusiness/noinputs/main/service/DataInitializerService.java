package cphbusiness.noinputs.main.service;

import cphbusiness.noinputs.main.model.FoodItem;
import cphbusiness.noinputs.main.model.Restaurant;
import cphbusiness.noinputs.main.repository.RestaurantRepository;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataInitializerService {

    // This class persist data to the database (Only for demonstration purposes)
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public DataInitializerService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @PostConstruct
    public void init() {
        // Persist data to the database

        for (int i = 0; i < 10; i++) {
            Faker faker = new Faker();
            Restaurant restaurant = new Restaurant(faker.restaurant().name(), faker.address().fullAddress(), faker.phoneNumber().cellPhone(), faker.internet().emailAddress());
            restaurant = restaurantRepository.save(restaurant);

            List<FoodItem> menu = new ArrayList<>();
            for (int j = 1; j < 11; j++) {
                menu.add(new FoodItem(faker.food().dish(), faker.number().numberBetween(10, 200), restaurant, (long) j));
            }

            restaurant.setMenu(menu);
            restaurantRepository.save(restaurant);
        }
    }
}
