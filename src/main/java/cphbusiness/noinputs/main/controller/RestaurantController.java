package cphbusiness.noinputs.main.controller;

import cphbusiness.noinputs.main.dto.RestaurantDTO;
import cphbusiness.noinputs.main.exception.NotAuthorizedException;
import cphbusiness.noinputs.main.exception.RestaurantNotFoundException;
import cphbusiness.noinputs.main.facade.ServiceFacade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://167.71.45.53:3000"}, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    private final ServiceFacade serviceFacade;

    @Autowired
    public RestaurantController(ServiceFacade serviceFacade) {
        this.serviceFacade = serviceFacade;
    }

    @GetMapping(value = "/restaurants", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        List<RestaurantDTO> restaurantList = serviceFacade.getAllRestaurants();

        return new ResponseEntity<>(restaurantList, HttpStatus.OK);
    }

    @GetMapping(value = "/restaurants/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RestaurantDTO> getRestaurant(@Valid @PathVariable Long id) {
        RestaurantDTO restaurant;
        try {
            restaurant = serviceFacade.getRestaurant(id);
        } catch(RestaurantNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PostMapping(value = "/restaurants", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RestaurantDTO> createRestaurant(@Valid @CookieValue("jwt-token") String jwtToken, @RequestBody RestaurantDTO restaurantDTO) {
        RestaurantDTO restaurant = null;
        try {
            restaurant = serviceFacade.createRestaurant(jwtToken, restaurantDTO);
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }
}
