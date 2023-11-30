package cphbusiness.noinputs.main.service;

import cphbusiness.noinputs.main.exception.FoodItemNotFoundException;
import cphbusiness.noinputs.main.exception.RestaurantNotFoundException;

import java.util.Map;

public interface OrderService {
    void createOrder(Long userId, Long restaurantId, Map<Integer, Long> foodItems) throws RestaurantNotFoundException, FoodItemNotFoundException;
}
