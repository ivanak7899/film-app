package com.film_service;

import com.film_service.storage.Film;
import com.film_service.storage.FilmRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

@Service
public class FilmService {
    private final FilmRepository repository;

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
}
