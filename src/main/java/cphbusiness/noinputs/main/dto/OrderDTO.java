package cphbusiness.noinputs.main.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Map;

public class OrderDTO {

    @NotNull
    private Long restaurantId;

    @NotNull
    private Map<Integer, Long> foodItems;

    public OrderDTO() {
    }

    public OrderDTO(Long restaurantId, Map<Integer, Long> foodItems) {
        this.restaurantId = restaurantId;
        this.foodItems = foodItems;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Map<Integer, Long> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(Map<Integer, Long> foodItems) {
        this.foodItems = foodItems;
    }
}
