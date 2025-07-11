package com.review_service;

import com.review_service.storage.Review;
import com.review_service.storage.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    private final String filmServiceUrlBase = "http://film-service:8081/films/";

    public ReviewService(ReviewRepository repository) {
        this.repository = repository;
    }

    public Review createReview(Review review) {
        // Check if the film ID exists by making a GET request to film-service
        try {
            restTemplate.getForObject(filmServiceUrlBase + review.getFilmId(), String.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException("Film with ID " + review.getFilmId() + " does not exist.");
        }

        return repository.save(review);
    }

    public List<Review> getReviewsByFilmId(Long filmId) {
        return repository.findByFilmId(filmId);
    }
    public List<Review> getReviewsByReviewerName(String reviewerName) {
        return repository.findByReviewerNameIgnoreCaseContaining(reviewerName);
    }

    public Optional<Review> getById(Long id) {
        return repository.findById(id);
    }

    public List<Review> getAllReviews() {
        return repository.findAll();
    }

    public boolean deleteReview(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
