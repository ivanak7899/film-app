package com.film_service.api;

import com.film_service.FilmService;
import com.film_service.storage.Film;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.jpa.domain.Specification;
import com.film_service.storage.FilmSpecification;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService service;

    public FilmController(FilmService service) {
        this.service = service;
    }

    @GetMapping
    public List<Film> getFilms(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String director) {

        Specification<Film> spec = Specification.where(FilmSpecification.hasYear(year))
                .and(FilmSpecification.hasGenre(genre))
                .and(FilmSpecification.hasDirector(director));

        return service.getAllFilms(spec);
    }

    @PostMapping
    public Film add(@RequestBody Film film) {
        return service.createFilm(film);
    }
}
