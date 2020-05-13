package com.sskim.eatgo.application;

import com.sskim.eatgo.domain.Review;
import com.sskim.eatgo.domain.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    public Review addReview(Long RestaurantId, Review review) {
        review.setRestaurantId(RestaurantId);
        return reviewRepository.save(review);
    }
}
