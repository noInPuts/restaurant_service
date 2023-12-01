package cphbusiness.noinputs.main.service;

import cphbusiness.noinputs.main.dto.OrderFoodItemDTO;
import cphbusiness.noinputs.main.exception.FoodItemNotFoundException;
import cphbusiness.noinputs.main.exception.RestaurantNotFoundException;
import cphbusiness.noinputs.main.model.FoodItem;
import cphbusiness.noinputs.main.model.Order;
import cphbusiness.noinputs.main.model.OrderFoodItem;
import cphbusiness.noinputs.main.model.Restaurant;
import cphbusiness.noinputs.main.repository.OrderRepository;
import cphbusiness.noinputs.main.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final RestaurantRepository restaurantRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(RestaurantRepository restaurantRepository, OrderRepository orderRepository) {
        this.restaurantRepository = restaurantRepository;
        this.orderRepository = orderRepository;
    }

    public void createOrder(Long userId, Long restaurantId, List<OrderFoodItemDTO> foodItems) throws RestaurantNotFoundException, FoodItemNotFoundException {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
        List<OrderFoodItem> orderFoodItems = new ArrayList<>();
        Order order = new Order();

        if(restaurantOptional.isPresent()) {
            List<FoodItem> foodItemList = restaurantOptional.get().getMenu();
            for (OrderFoodItemDTO orderFoodItemDTO : foodItems) {
                Optional<FoodItem> foodItemOptional = foodItemList.stream().filter(foodItem -> foodItem.getFoodItemId().equals(orderFoodItemDTO.getId())).findFirst();

                if(foodItemOptional.isPresent()) {
                    orderFoodItems.add(new OrderFoodItem(foodItemOptional.get(), orderFoodItemDTO.getQuantity()));
                } else {
                    throw new FoodItemNotFoundException("Food item not found");
                }
            }
        } else {
            throw new RestaurantNotFoundException("Restaurant not found");
        }

        order.setCustomerId(userId);
        order.setRestaurant(restaurantOptional.get());
        order = orderRepository.save(order);

        for (OrderFoodItem orderFoodItem : orderFoodItems) {
            orderFoodItem.setOrder(order);
        }

        order.setFoodItems(orderFoodItems);

        orderRepository.save(order);
    }
}
