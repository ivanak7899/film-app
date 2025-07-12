package com.film_service;

import com.film_service.storage.Film;
import com.film_service.storage.FilmRepository;
import com.film_service.dto.Review;
import com.film_service.exceptions.FilmNotFoundException;
import com.film_service.exceptions.NoReviewsException;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;

@Service
public class FilmService {
    private final FilmRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public FilmService(FilmRepository repository) {
        this.repository = repository;
    }

    public List<Film> getAllFilms(Specification<Film> spec) {
        return repository.findAll(spec);
    }

    public Film createFilm(Film film) {
        return repository.save(film);
    }

    public Optional<Film> getFilmById(Long id) {
        return repository.findById(id);
    }

    public boolean deleteFilm(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Cacheable(value = "filmRatings", key = "#filmId")
    public Double getAverageRating(Long filmId) {
        // Check if film exists
        Optional<Film> film = repository.findById(filmId);
        if (film.isEmpty()) {
            throw new FilmNotFoundException(filmId);
        }

        // Call review-service to get reviews
        String url = "http://review-service:8080/reviews?filmId=" + filmId;

        ResponseEntity<List<Review>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Review>>() {}
        );

        List<Review> reviews = response.getBody();

        if (reviews == null || reviews.isEmpty()) {
            throw new NoReviewsException(filmId);
        }

        return reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }
}
