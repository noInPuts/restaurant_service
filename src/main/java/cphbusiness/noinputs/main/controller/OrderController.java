package cphbusiness.noinputs.main.controller;

import cphbusiness.noinputs.main.dto.OrderDTO;
import cphbusiness.noinputs.main.exception.FoodItemNotFoundException;
import cphbusiness.noinputs.main.exception.RestaurantNotFoundException;
import cphbusiness.noinputs.main.service.JwtService;
import cphbusiness.noinputs.main.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "3000")
@RestController
public class OrderController {

    private final OrderService orderService;

    private final JwtService jwtService;

    @Autowired
    public OrderController(OrderService orderService, JwtService jwtService) {
            this.orderService = orderService;
            this.jwtService = jwtService;
    }

    @PostMapping(value = "/order", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@Valid @RequestBody OrderDTO orderDTO, @Valid @CookieValue("jwtToken") String jwtToken) throws RestaurantNotFoundException, FoodItemNotFoundException {
        Long userId = jwtService.getUserIdFromJwtToken(jwtToken);


        // TODO: Handle exception
        orderService.createOrder(userId, orderDTO.getRestaurantId(), orderDTO.getFoodItems());
    }
}
