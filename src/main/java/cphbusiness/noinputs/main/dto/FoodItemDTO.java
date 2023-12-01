package cphbusiness.noinputs.main.dto;

import jakarta.validation.constraints.NotNull;

public class FoodItemDTO {

    @NotNull(message = "Food item id cannot be null")
    private Long id;

    private String name;
    private Long price;

    public FoodItemDTO() {
    }

    public FoodItemDTO(Long id, String name, Long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
