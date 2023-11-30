package cphbusiness.noinputs.main.service;

import cphbusiness.noinputs.main.exception.FoodItemNotFoundException;
import cphbusiness.noinputs.main.exception.RestaurantNotFoundException;
import cphbusiness.noinputs.main.model.FoodItem;
import cphbusiness.noinputs.main.model.Restaurant;
import cphbusiness.noinputs.main.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public OrderServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }


    // TODO: Change so it uses id instead of parsing JWT Token
    public void createOrder(Long userId, Long restaurantId, Map<Integer, Long> foodItems) throws RestaurantNotFoundException, FoodItemNotFoundException {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
        Map<Integer, FoodItem> foodItemMap = new HashMap<>();

        if(restaurantOptional.isPresent()) {
            List<FoodItem> foodItemList = restaurantOptional.get().getMenu();
            for (Map.Entry<Integer, Long> entry : foodItems.entrySet()) {
                Optional<FoodItem> foodItemOptional = foodItemList.stream().findFirst().filter(foodItem -> foodItem.getId().equals(entry.getValue()));

                if(foodItemOptional.isPresent()) {
                    foodItemMap.put(entry.getKey(), foodItemOptional.get());
                } else {
                    throw new FoodItemNotFoundException("Food item not found");
                }
            }
        } else {
            throw new RestaurantNotFoundException("Restaurant not found");
        }

        // TODO: Send request to OrderService
        // foodItemMap
        // userId
        // restaurantId
    }
}
