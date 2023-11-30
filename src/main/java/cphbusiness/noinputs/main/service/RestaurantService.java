package cphbusiness.noinputs.main.service;

import cphbusiness.noinputs.main.dto.RestaurantDTO;
import cphbusiness.noinputs.main.exception.RestaurantNotFoundException;

import java.util.List;

public interface RestaurantService {
    List<RestaurantDTO> getAllRestaurants();
    RestaurantDTO getRestaurant(Long id) throws RestaurantNotFoundException;
}
