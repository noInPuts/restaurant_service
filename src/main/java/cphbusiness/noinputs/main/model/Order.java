package cphbusiness.noinputs.main.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    private Restaurant restaurant;

    @JoinColumn(name = "food_items")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderFoodItem> foodItems;

    @Column(nullable = false)
    private Long customerId;

    public Order() {
    }

    public Order(Restaurant restaurant, List<OrderFoodItem> foodItems, Long customerId) {
        this.restaurant = restaurant;
        this.foodItems = foodItems;
        this.customerId = customerId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<OrderFoodItem> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(List<OrderFoodItem> foodItems) {
        this.foodItems = foodItems;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
