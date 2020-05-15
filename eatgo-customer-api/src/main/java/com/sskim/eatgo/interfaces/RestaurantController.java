package com.sskim.eatgo.interfaces;

import com.sskim.eatgo.application.RestaurantService;
import com.sskim.eatgo.domain.Restaurant;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService){
        this.restaurantService =restaurantService;
    }

    @GetMapping("/restaurants")
    public List<Restaurant> list(
            @RequestParam("region") String region,
            @RequestParam("category") Long categoryId){
        return restaurantService.getRestaurantList(region, categoryId);
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant detail(@PathVariable("id") Long id){
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        return restaurant;
    }
}