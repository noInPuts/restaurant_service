package cphbusiness.noinputs.main.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class OrderDTO {

    @NotNull(message = "Restaurant id cannot be null")
    private Long restaurantId;

    @NotNull(message = "Food items cannot be null")
    @NotEmpty(message = "Food items cannot be empty")
    private List<OrderFoodItemDTO> foodItems;

    public OrderDTO() {
    }

    public OrderDTO(Long restaurantId, List<OrderFoodItemDTO> foodItems) {
        this.restaurantId = restaurantId;
        this.foodItems = foodItems;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<OrderFoodItemDTO> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(List<OrderFoodItemDTO> foodItems) {
        this.foodItems = foodItems;
    }
}
