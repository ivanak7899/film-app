package com.film_service.api;

import com.film_service.FilmService;
import com.film_service.storage.Film;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;

@WebMvcTest(FilmController.class)
class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmService filmService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testAddFilm() throws Exception {
        Film film = new Film("The Shining", "Horror", 1980, "Stanley Kubrick");
        film.setId(1L);

        Mockito.when(filmService.createFilm(any(Film.class))).thenReturn(film);

        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("The Shining"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testGetFilms() throws Exception {
        Film f1 = new Film("2001: A Space Odyssey", "Sci-Fi", 1968, "Stanley Kubrick");
        Film f2 = new Film("A Clockwork Orange", "Drama", 1971, "Stanley Kubrick");

        Mockito.when(filmService.getAllFilms(any())).thenReturn(Arrays.asList(f1, f2));

        mockMvc.perform(get("/films"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("2001: A Space Odyssey"))
                .andExpect(jsonPath("$[1].title").value("A Clockwork Orange"));
    }

    @Test
    void testGetFilmsWithFilters() throws Exception {
        Film filteredFilm = new Film("Paths of Glory", "Drama", 1957, "Stanley Kubrick");

        Mockito.when(filmService.getAllFilms(any())).thenReturn(List.of(filteredFilm));

        mockMvc.perform(get("/films")
                        .param("genre", "Drama")
                        .param("director", "kubrick"))  // Partial match
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Paths of Glory"))
                .andExpect(jsonPath("$[0].director").value("Stanley Kubrick"));
    }
}
