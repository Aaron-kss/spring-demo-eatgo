package com.sskim.eatgo.application;

import com.sskim.eatgo.domain.Restaurant;
import com.sskim.eatgo.domain.RestaurantNotFoundException;
import com.sskim.eatgo.domain.RestaurantRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class RestaurantServiceTest {

    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Before
    public void startUp(){
        MockitoAnnotations.initMocks(this);
        mockRestaurantRepository();
        restaurantService = new RestaurantService(restaurantRepository);
    }

    private void mockRestaurantRepository() {
        List<Restaurant> restaurantList = new ArrayList<>();
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .categoryId(1L)
                .build();
        restaurantList.add(restaurant);
        given(restaurantRepository.findAll()).willReturn(restaurantList);
        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));
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

        assertThat(restaurant.getId(), is(1004L));
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
                .categoryId(1L)
                .build();

        Restaurant saved = Restaurant.builder()
                .id(1234L)
                .name("Beyong")
                .address("Busan")
                .categoryId(1L)
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
                .categoryId(1L)
                .build();

        given(restaurantRepository.findById(1004L))
                .willReturn(Optional.of(restaurant));

        restaurantService.updateRestaurant(1004L, "Sool zip", "Busan");

        assertThat(restaurant.getName(), is("Sool zip"));
    }
}