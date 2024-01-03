package cphbusiness.noinputs.main.facade;

import cphbusiness.noinputs.main.dto.RestaurantDTO;
import cphbusiness.noinputs.main.exception.NotAuthorizedException;
import cphbusiness.noinputs.main.exception.RestaurantNotFoundException;

import java.util.List;

public interface ServiceFacade {
    List<RestaurantDTO> getAllRestaurants();
    RestaurantDTO getRestaurant(Long id) throws RestaurantNotFoundException;
    RestaurantDTO createRestaurant(String jwtToken, RestaurantDTO restaurantDTO) throws NotAuthorizedException;
}
