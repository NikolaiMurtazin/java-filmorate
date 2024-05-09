package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmRepository;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserRepository;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class FilmControllerTest {
    private FilmController filmController;
    private Validator validator;
    private Film film;

    @BeforeEach
    void setUp() {
        filmController = new FilmController(new FilmService(new InMemoryFilmRepository(), new InMemoryUserRepository()));
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        film = Film.builder()
                .name("Test Film")
                .description("Test description")
                .releaseDate(LocalDate.of(2024, 3,8))
                .duration(120)
                .build();
    }

    @Test
    void testCreate() {
        Film createdFilm = filmController.save(film);

        Set<ConstraintViolation<Film>> violations = validator.validate(createdFilm);

        assertThat(violations).isEmpty();
        assertThat(film).isEqualTo(createdFilm);
    }

    @Test
    void testBlankMovieDescription() {
        film.setDescription(" ");

        Film createdFilm = filmController.save(film);

        Set<ConstraintViolation<Film>> violations = validator.validate(createdFilm);

        assertThat(violations).hasSize(1);
    }

    @Test
    void testMovieDescriptionLength() {
        film.setDescription("In the heart of a bustling city, a young artist discovers an old, " +
                "mysterious journal hidden in her attic. As she delves into its pages, " +
                "she's transported back to 1920s Paris, where she unravels a tale of passion, " +
                "betrayal, and a love that transcends time. Will she uncover the truth before it's too late?");

        Film createdFilm = filmController.save(film);

        Set<ConstraintViolation<Film>> violations = validator.validate(createdFilm);

        assertThat(violations).hasSize(1);
    }

    @Test
    void testSave() {
        film.setReleaseDate(LocalDate.of(1895, 12, 27));

        assertThatThrownBy(() -> filmController.save(film), String.valueOf(ValidationException.class));
    }

    @Test
    void testCheckMovieDuration() {
        film.setDuration(0);

        Film createdFilm = filmController.save(film);

        Set<ConstraintViolation<Film>> violations = validator.validate(createdFilm);

        assertThat(violations).hasSize(1);
    }

    @Test
    void testChange() {
        Film createdFilm = filmController.save(film);
        createdFilm.setName("test");

        Set<ConstraintViolation<Film>> violations = validator.validate(createdFilm);

        assertThat(violations).isEmpty();
        assertThat(film).isEqualTo(createdFilm);
    }
}