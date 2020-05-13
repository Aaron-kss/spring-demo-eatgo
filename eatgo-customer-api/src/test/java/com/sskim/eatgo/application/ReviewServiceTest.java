package com.sskim.eatgo.application;

import com.sskim.eatgo.application.ReviewService;
import com.sskim.eatgo.domain.Review;
import com.sskim.eatgo.domain.ReviewRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ReviewServiceTest {

    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        reviewService = new ReviewService(reviewRepository);
    }

    @Test
    public void addReview(){
        Review review = Review.builder()
                .name("aaron")
                .score(3)
                .description("soso")
                .build();

        reviewService.addReview(1004L, review);
    }
}