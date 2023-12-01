package cphbusiness.noinputs.main.service;

import cphbusiness.noinputs.main.dto.OrderFoodItemDTO;
import cphbusiness.noinputs.main.exception.FoodItemNotFoundException;
import cphbusiness.noinputs.main.exception.RestaurantNotFoundException;

import java.util.List;

public interface OrderService {
    void createOrder(Long userId, Long restaurantId, List<OrderFoodItemDTO> foodItems) throws RestaurantNotFoundException, FoodItemNotFoundException;
}
