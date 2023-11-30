package cphbusiness.noinputs.main.controller;

import cphbusiness.noinputs.main.dto.RestaurantDTO;
import cphbusiness.noinputs.main.exception.RestaurantNotFoundException;
import cphbusiness.noinputs.main.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "3000")
@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping(value = "/restaurants", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        List<RestaurantDTO> restaurantList = restaurantService.getAllRestaurants();

        return new ResponseEntity<>(restaurantList, HttpStatus.OK);
    }

    @GetMapping(value = "/restaurants/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RestaurantDTO> getRestaurant(@Valid @PathVariable Long id) {
        RestaurantDTO restaurant;
        try {
            restaurant = restaurantService.getRestaurant(id);
        } catch(RestaurantNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
