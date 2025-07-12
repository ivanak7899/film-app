package com.film_service.exceptions;

public class NoReviewsException extends RuntimeException {
    public NoReviewsException(Long id) {
        super("No reviews available for film ID " + id);
    }
}
