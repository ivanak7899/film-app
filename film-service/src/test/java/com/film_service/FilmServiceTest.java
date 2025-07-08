package com.film_service;

import com.film_service.storage.Film;
import com.film_service.storage.FilmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FilmServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @InjectMocks
    private FilmService filmService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateFilm() {
        Film film = new Film("The Shining", "Horror", 1980, "Stanley Kubrick");

        when(filmRepository.save(any(Film.class))).thenAnswer(invocation -> {
            Film saved = invocation.getArgument(0);
            saved.setId(1L); // simulate generated ID
            return saved;
        });

        Film result = filmService.createFilm(film);

        assertNotNull(result.getId());
        assertEquals("The Shining", result.getTitle());
        verify(filmRepository).save(film);
    }

    @Test
    void testGetAllFilms() {
        Film f1 = new Film("2001: A Space Odyssey", "Sci-Fi", 1968, "Stanley Kubrick");
        Film f2 = new Film("A Clockwork Orange", "Drama", 1971, "Stanley Kubrick");

        when(filmRepository.findAll(Mockito.<Specification<Film>>any()))
                .thenReturn(Arrays.asList(f1, f2));

        List<Film> results = filmService.getAllFilms(null);

        assertEquals(2, results.size());
        verify(filmRepository).findAll(Mockito.<Specification<Film>>any());
    }
}
