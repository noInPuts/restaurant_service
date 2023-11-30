package cphbusiness.noinputs.main.dto;

import java.util.List;

public class RestaurantDTO {
    private String name;
    private Long id;

    private List<FoodItemDTO> menu;

    private String address;

    private String phone;

    private String email;

    public RestaurantDTO() {
    }

    public RestaurantDTO(Long id, String name, String address, String phone, String email) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public RestaurantDTO(Long id, String name, String address, String phone, String email, List<FoodItemDTO> menu) {
        this.name = name;
        this.id = id;
        this.menu = menu;
        this.address = address;
        this.phone = phone;
        this.email = email;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
