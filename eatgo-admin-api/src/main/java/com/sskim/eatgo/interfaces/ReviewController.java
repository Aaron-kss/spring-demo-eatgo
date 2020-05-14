package com.sskim.eatgo.interfaces;

import com.sskim.eatgo.application.ReviewService;
import com.sskim.eatgo.domain.Review;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews")
    public List<Review> list(){
        return reviewService.getReviews();
    }
}