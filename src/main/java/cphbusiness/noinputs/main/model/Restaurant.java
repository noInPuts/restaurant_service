package cphbusiness.noinputs.main.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant", fetch = FetchType.EAGER)
    private List<FoodItem> menu;

    public Restaurant() {
    }

    public Restaurant(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Restaurant(long id, String name, List<FoodItem> menu) {
        this.id = id;
        this.name = name;
        this.menu = menu;
    }

    public Restaurant(String name, List<FoodItem> menu) {
        this.name = name;
        this.menu = menu;
    }

    public Restaurant(String name) {
        this.name = name;
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

    public List<FoodItem> getMenu() {
        return menu;
    }

    public void setMenu(List<FoodItem> menu) {
        this.menu = menu;
    }
}
