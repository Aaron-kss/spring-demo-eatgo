package com.sskim.eatgo.interfaces;

import com.sskim.eatgo.application.RestaurantService;
import com.sskim.eatgo.domain.MenuItem;
import com.sskim.eatgo.domain.Restaurant;
import com.sskim.eatgo.domain.RestaurantNotFoundException;
import com.sskim.eatgo.domain.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void list() throws Exception {
        List<Restaurant> restaurantList = new ArrayList<>();

        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .categoryId(1L)
                .build();

        restaurantList.add(restaurant);
        given(restaurantService.getRestaurantList("Seoul", 1L)).willReturn(restaurantList);
        
        mvc.perform(get("/restaurants?region=Seoul&category=1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"Bob zip\"")
                ));
    }

    @Test
    public void detailWithExisted() throws Exception {

        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("JOKER House")
                .address("Seoul")
                .build();

        MenuItem menuItem = MenuItem.builder()
                .name("Kimchi")
                .build();

        restaurant.setMenuItemList(Arrays.asList(menuItem));
        Review review = Review.builder()
                .name("aaron")
                .score(3)
                .description("soso")
                .build();
        restaurant.setReviews(Arrays.asList(review));

        given(restaurantService.getRestaurantById(1004L)).willReturn(restaurant);
        
        mvc.perform(get("/restaurants/1004"))
                .andExpect(content().string(
                        containsString("\"id\":1004")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"JOKER House\"")
                ))
                .andExpect(content().string(
                        containsString("Kimchi")
                ))
                .andExpect(content().string(
                        containsString("soso")
                ));
    }

    @Test
    public void detailWithNotExisted() throws Exception {

        given(restaurantService.getRestaurantById(404L))
                .willThrow(new RestaurantNotFoundException(404L));

        mvc.perform(get("/restaurants/404"))
                .andExpect(status().isNotFound());
    }
}