package cphbusiness.noinputs.main.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "food_items")
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Food item id cannot be null")
    private Long foodItemId;

    @NotEmpty(message = "Food item name cannot be null")
    private String name;

    @NotNull(message = "Food item price cannot be null")
    private Long price;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public FoodItem() {
    }

    public FoodItem(long id, String dish, long price) {
        this.id = id;
        this.name = dish;
        this.price = price;
    }

    public FoodItem(String dish, long price) {
        this.name = dish;
        this.price = price;
    }

    public FoodItem(String name, long price, Restaurant restaurant, Long foodItemId) {
        this.name = name;
        this.price = price;
        this.restaurant = restaurant;
        this.foodItemId = foodItemId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Long getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(Long foodItemId) {
        this.foodItemId = foodItemId;
    }
}
