package com.film_service.api;

import com.film_service.FilmService;
import com.film_service.storage.Film;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.jpa.domain.Specification;
import com.film_service.storage.FilmSpecification;
import java.util.List;
import org.springframework.http.ResponseEntity;
import com.film_service.exceptions.FilmNotFoundException;
import jakarta.validation.Valid;

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

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable Long id) {
        return service.getFilmById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new FilmNotFoundException(id));
    }

    @PostMapping
    public Film add(@RequestBody @Valid Film film) {
        return service.createFilm(film);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable Long id) {
        if (service.deleteFilm(id)) {
            return ResponseEntity.noContent().build();
        } else {
            throw new FilmNotFoundException(id);
        }
    }
}
