package com.sskim.eatgo.application;

import com.sskim.eatgo.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final ReviewRepository reviewRepository;

    public RestaurantService(RestaurantRepository restaurantRepository,
                             MenuItemRepository menuItemRepository,
                             ReviewRepository reviewRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.reviewRepository = reviewRepository;
    }

    public Restaurant getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
        List<MenuItem> menuItemList = menuItemRepository.findAllByRestaurantId(id);
        restaurant.setMenuItemList(menuItemList);

        List<Review> reviews = reviewRepository.findAllByRestaurantId(id);
        restaurant.setReviews(reviews);

        return restaurant;
    }

    public List<Restaurant> getRestaurantList(String region, Long categoryId) {

        List<Restaurant> restaurants = restaurantRepository.findByAddressContainingAndCategoryId(region, categoryId);

        return Optional.ofNullable(restaurants).orElse(Collections.emptyList());
    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        Restaurant saved = restaurantRepository.save(restaurant);
        return saved;
    }

    @Transactional
    public Restaurant updateRestaurant(Long id, String name, String address) {

        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
        restaurant.updateInformation(name, address);

        return Restaurant.builder()
                .name(name)
                .address(address)
                .build();
    }
}