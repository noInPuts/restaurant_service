package cphbusiness.noinputs.main.dto;

import java.util.List;

public class RestaurantDTO {
    private String name;
    private Long id;

    private List<FoodItemDTO> menu;

    public RestaurantDTO() {
    }

    public RestaurantDTO(String name) {
        this.name = name;
    }

    public RestaurantDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public RestaurantDTO(Long id, String name, List<FoodItemDTO> menu) {
        this.id = id;
        this.name = name;
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<FoodItemDTO> getMenu() {
        return menu;
    }

    public void setMenus(List<FoodItemDTO> menu) {
        this.menu = menu;
    }
}
