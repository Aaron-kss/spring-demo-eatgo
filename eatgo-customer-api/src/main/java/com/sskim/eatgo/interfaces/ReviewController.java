package com.sskim.eatgo.interfaces;

import com.sskim.eatgo.application.ReviewService;
import com.sskim.eatgo.domain.Review;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @PostMapping("/restaurants/{restaurantId}/reviews")
    public ResponseEntity create(
            Authentication authentication,
            @PathVariable("restaurantId") Long restaurantId,
            @Valid @RequestBody Review resource
    ) throws URISyntaxException {

        Claims claims = (Claims) authentication.getPrincipal();

        String name = claims.get("name", String.class);
        Integer score = resource.getScore();
        String description = resource.getDescription();

        Review review = reviewService.addReview(restaurantId, name, score, description);

        return ResponseEntity
                .created(new URI("/restaurants/" + restaurantId + "/reviews/" + review.getId()))
                .body("{}");
    }
}
