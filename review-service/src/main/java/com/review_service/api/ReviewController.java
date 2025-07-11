package com.review_service.api;

import com.review_service.ReviewService;
import com.review_service.storage.Review;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.review_service.exceptions.ReviewNotFoundException;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @PostMapping
    public Review add(@RequestBody @Valid Review review) {
        return service.createReview(review);
    }

    @GetMapping
    public List<Review> all(@RequestParam(required = false) Long filmId,
                            @RequestParam(required = false) String reviewerName) {
        if (filmId != null) {
            return service.getReviewsByFilmId(filmId);
        } else if (reviewerName != null) {
            return service.getReviewsByReviewerName(reviewerName);
        }
        return service.getAllReviews();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ReviewNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        if (service.deleteReview(id)) {
            return ResponseEntity.noContent().build();
        } else {
            throw new ReviewNotFoundException(id);
        }
    }
}
