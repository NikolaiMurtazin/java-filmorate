package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.*;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class FilmControllerTest {
    private FilmController filmController;
    private Validator validator;

    @BeforeEach
    void setUp() {
        filmController = new FilmController();
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void testCreate() {
        Film film = Film.builder()
                .name("Test Film")
                .description("Test description")
                .releaseDate(LocalDate.of(2024, 3,8))
                .duration(120)
                .build();

        Film createdFilm = filmController.create(film);

        Set<ConstraintViolation<Film>> violations = validator.validate(createdFilm);

        assertThat(violations.size()).isEqualTo(0);
        assertThat(film).isEqualTo(createdFilm);
    }

    @Test
    public void testBlankMovieDescription() {
        Film film = Film.builder()
                .name("Test Film")
                .description("  ")
                .releaseDate(LocalDate.of(2024, 3,8))
                .duration(120)
                .build();

        Film createdFilm = filmController.create(film);

        Set<ConstraintViolation<Film>> violations = validator.validate(createdFilm);

        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void movieDescriptionLengthTest() {
        Film film = Film.builder()
                .name("Test Film")
                .description("In the heart of a bustling city, a young artist discovers an old, " +
                        "mysterious journal hidden in her attic. As she delves into its pages, " +
                        "she's transported back to 1920s Paris, where she unravels a tale of passion, " +
                        "betrayal, and a love that transcends time. Will she uncover the truth before it's too late?")
                .releaseDate(LocalDate.of(2024, 3,8))
                .duration(120)
                .build();

        Film createdFilm = filmController.create(film);

        Set<ConstraintViolation<Film>> violations = validator.validate(createdFilm);

        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void testCreateFilmWithReleaseDateBeforeFirstFilm() {
        Film film = Film.builder()
                .name("Test Film")
                .description("Test description")
                .releaseDate(LocalDate.of(1895, 12, 27))
                .duration(120)
                .build();

        assertThatThrownBy(() -> filmController.create(film), String.valueOf(ValidationException.class));
    }

    @Test
    public void testChange() {
        Film film = Film.builder()
                .name("Test Film")
                .description("Test description")
                .releaseDate(LocalDate.of(2024, 3,8))
                .duration(120)
                .build();

        Film createdFilm = filmController.create(film);
        createdFilm.setName("test");

        Set<ConstraintViolation<Film>> violations = validator.validate(createdFilm);

        assertThat(violations.size()).isEqualTo(0);
        assertThat(film).isEqualTo(createdFilm);
    }
}