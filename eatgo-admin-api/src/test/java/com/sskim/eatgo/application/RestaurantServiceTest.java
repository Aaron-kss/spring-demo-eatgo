package com.sskim.eatgo.application;

import com.sskim.eatgo.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class RestaurantServiceTest {

    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Before
    public void startUp(){
        MockitoAnnotations.initMocks(this);
        mockRestaurantRepository();
        mockMenuItemRepository();
        mockReviewRepository();
        restaurantService = new RestaurantService(restaurantRepository, menuItemRepository, reviewRepository);
    }

    private void mockRestaurantRepository() {
        List<Restaurant> restaurantList = new ArrayList<>();
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();
        restaurantList.add(restaurant);
        given(restaurantRepository.findAll()).willReturn(restaurantList);
        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));
    }

    private void mockMenuItemRepository(){
        List<MenuItem> menuItemList = new ArrayList<>();

        MenuItem menuItem = MenuItem.builder()
                .name("Kimchi")
                .build();

        menuItemList.add(menuItem);
        given(menuItemRepository.findAllByRestaurantId(1004L)).willReturn(menuItemList);
    }

    private void mockReviewRepository() {
        List<Review> reviews = new ArrayList<>();

        Review review = Review.builder()
                .name("aaron")
                .score(1)
                .description("bad")
                .build();

        reviews.add(review);

        given(reviewRepository.findAllByRestaurantId(1004L)).willReturn(reviews);
    }

    @Test
    public void getRestaurantList(){
        List<Restaurant> restaurantList = restaurantService.getRestaurantList();
        Restaurant restaurant = restaurantList.get(0);
        assertThat(restaurant.getId(), is(1004L));
    }

    @Test
    public void getRestaurantWithExisted(){
        Restaurant restaurant = restaurantService.getRestaurantById(1004L);

        verify(menuItemRepository).findAllByRestaurantId(eq(1004L));
        verify(reviewRepository).findAllByRestaurantId(eq(1004L));

        assertThat(restaurant.getId(), is(1004L));

        MenuItem menuItem = restaurant.getMenuItemList().get(0);
        Review review = restaurant.getReviews().get(0);

        assertThat(menuItem.getName(), is("Kimchi"));
        assertThat(review.getDescription(), is("bad"));
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void getRestaurantWithNotExisted(){
        restaurantService.getRestaurantById(404L);
    }

    @Test
    public void addRestaurant(){
        Restaurant restaurant = Restaurant.builder()
                .name("Beyong")
                .address("Busan")
                .build();

        Restaurant saved = Restaurant.builder()
                .id(1234L)
                .name("Beyong")
                .address("Busan")
                .build();


        given(restaurantRepository.save(any())).willReturn(saved);

        Restaurant created =  restaurantService.addRestaurant(restaurant);

        assertThat(created.getId(), is(1234L));
    }


    @Test
    public void updateRestaurant(){
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();

        given(restaurantRepository.findById(1004L))
                .willReturn(Optional.of(restaurant));

        restaurantService.updateRestaurant(1004L, "Sool zip", "Busan");

        assertThat(restaurant.getName(), is("Sool zip"));
    }
}