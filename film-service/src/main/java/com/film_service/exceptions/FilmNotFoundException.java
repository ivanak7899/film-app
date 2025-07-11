package com.film_service.exceptions;

public class FilmNotFoundException extends RuntimeException {
    public FilmNotFoundException(Long id) {
        super("Film with ID " + id + " not found.");
    }
}
