package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmControllerTest {

    private FilmController filmController;

    @BeforeEach
    void setUp() {
        filmController = new FilmController();
    }
    @Test
    public void testCreate() {
        // Создаем объект фильма с корректными данными
        Film film = new Film("Test Film", "Test description", "2023-01-01", 120);
        Film createdFilm = filmController.create(film);

        assertEquals(1, createdFilm.getId());
        assertEquals("Test Film", createdFilm.getName());
        assertEquals("Test description", createdFilm.getDescription());
        assertEquals("2023-01-01", createdFilm.getReleaseDate());
    }

    @Test
    public void testChange() {
        Film film = new Film("Test Film", "Test description", "2023-01-01", 120);
        Film createdFilm = filmController.create(film);
        createdFilm.setName("test");
        Film changedFilm = filmController.change(createdFilm);

        assertEquals(film, changedFilm);
    }
}