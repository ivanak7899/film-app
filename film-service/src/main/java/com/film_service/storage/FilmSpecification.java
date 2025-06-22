package com.film_service.storage;

import org.springframework.data.jpa.domain.Specification;
import com.film_service.storage.Film;

public class FilmSpecification {
    public static Specification<Film> hasYear(Integer year) {
        return (root, query, cb) ->
                year == null ? null : cb.equal(root.get("year"), year);
    }

    public static Specification<Film> hasGenre(String genre) {
        return (root, query, cb) ->
                genre == null ? null : cb.like(cb.lower(root.get("genre")), "%" + genre.toLowerCase() + "%");
    }

    public static Specification<Film> hasDirector(String director) {
        return (root, query, cb) ->
                director == null ? null : cb.like(cb.lower(root.get("director")), "%" + director.toLowerCase() + "%");
    }
}
