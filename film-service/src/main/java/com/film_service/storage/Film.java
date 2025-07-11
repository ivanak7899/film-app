package com.film_service.storage;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String genre;

    @Min(value = 1888)
    private int year;

    @NotBlank
    private String director;

    // Constructors
    public Film() {}

    public Film(String title, String genre, int year, String director) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.director = director;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getDirector() { return director; }
    public void setYear(String director) { this.director = director; }
}
