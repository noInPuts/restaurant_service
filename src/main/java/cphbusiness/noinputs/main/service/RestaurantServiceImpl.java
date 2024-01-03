package cphbusiness.noinputs.main.service;

import cphbusiness.noinputs.main.dto.FoodItemDTO;
import cphbusiness.noinputs.main.dto.RestaurantDTO;
import cphbusiness.noinputs.main.exception.RestaurantNotFoundException;
import cphbusiness.noinputs.main.model.FoodItem;
import cphbusiness.noinputs.main.model.Restaurant;
import cphbusiness.noinputs.main.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<RestaurantDTO> getAllRestaurants() {
        List<Restaurant> restaurantsEntities = restaurantRepository.findAll();
        List<RestaurantDTO> restaurantsDTOs = new ArrayList<>();

        for (Restaurant restaurant : restaurantsEntities) {
            restaurantsDTOs.add(new RestaurantDTO(restaurant.getId(), restaurant.getName(), restaurant.getAddress(), restaurant.getPhone(), restaurant.getEmail()));
        }

        return restaurantsDTOs;
    }

    public RestaurantDTO getRestaurant(Long id) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(
                () -> new RestaurantNotFoundException("Restaurant with id " + id + " not found"));

        List<FoodItemDTO> menu = new ArrayList<>();
        for (FoodItem foodItem : restaurant.getMenu()) {
            menu.add(new FoodItemDTO(foodItem.getId(), foodItem.getName(), foodItem.getPrice()));
        }

        return new RestaurantDTO(restaurant.getId(), restaurant.getName(), restaurant.getAddress(), restaurant.getPhone(), restaurant.getEmail() , menu);
    }

    @Override
    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = restaurantRepository.save(new Restaurant(restaurantDTO.getName(), "none", restaurantDTO.getPhone(), restaurantDTO.getEmail()));
        return new RestaurantDTO(restaurant.getId(), restaurant.getName(), restaurant.getAddress(), restaurant.getPhone(), restaurant.getEmail());
    }
}
