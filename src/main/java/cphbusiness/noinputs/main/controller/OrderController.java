package cphbusiness.noinputs.main.controller;

import cphbusiness.noinputs.main.dto.ErrorResponseDTO;
import cphbusiness.noinputs.main.dto.MessageResponseDTO;
import cphbusiness.noinputs.main.dto.OrderDTO;
import cphbusiness.noinputs.main.exception.FoodItemNotFoundException;
import cphbusiness.noinputs.main.exception.RestaurantNotFoundException;
import cphbusiness.noinputs.main.service.JwtService;
import cphbusiness.noinputs.main.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> createOrder(@Valid @RequestBody OrderDTO orderDTO, @Valid @CookieValue("jwt-token") String jwtToken) {
        Long userId = jwtService.getUserIdFromJwtToken(jwtToken);

        try {
            orderService.createOrder(userId, orderDTO.getRestaurantId(), orderDTO.getFoodItems());
        } catch (RestaurantNotFoundException | FoodItemNotFoundException e) {
            ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(e.getMessage());
            return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
        }

        MessageResponseDTO messageResponseDTO = new MessageResponseDTO("Order has been created");
        return new ResponseEntity<>(messageResponseDTO, HttpStatus.CREATED);
    }
}
