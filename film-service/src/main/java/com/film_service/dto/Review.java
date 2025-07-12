package com.film_service.dto;

public class Review {
    private Long id;
    private Long filmId;
    private String reviewerName;
    private String text;
    private Integer rating;

    // Getters and setters
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public Long getFilmId() { return filmId; }
    public void setFilmId(Long filmId) { this.filmId = filmId; }
}
