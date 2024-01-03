package cphbusiness.noinputs.main.facade;

import cphbusiness.noinputs.main.dto.RestaurantDTO;
import cphbusiness.noinputs.main.exception.NotAuthorizedException;
import cphbusiness.noinputs.main.exception.RestaurantNotFoundException;
import cphbusiness.noinputs.main.service.JwtService;
import cphbusiness.noinputs.main.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceFacadeImpl implements ServiceFacade {

    private final RestaurantService restaurantService;
    private final JwtService jwtService;

    @Autowired
    public ServiceFacadeImpl(RestaurantService restaurantService, JwtService jwtService) {
        this.restaurantService = restaurantService;
        this.jwtService = jwtService;
    }

    @Override
    public List<RestaurantDTO> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @Override
    public RestaurantDTO getRestaurant(Long id) throws RestaurantNotFoundException {
        return restaurantService.getRestaurant(id);
    }

    @Override
    public RestaurantDTO createRestaurant(String jwtToken, RestaurantDTO restaurantDTO) throws NotAuthorizedException {
        if(!jwtService.validateAdminAccount(jwtToken)) {
            throw new NotAuthorizedException("Not authorized");
        }

        return restaurantService.createRestaurant(restaurantDTO);
    }
}
