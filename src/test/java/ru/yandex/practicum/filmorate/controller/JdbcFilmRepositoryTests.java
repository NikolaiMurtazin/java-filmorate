package ru.yandex.practicum.filmorate.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.repository.film.JdbcFilmRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({JdbcFilmRepository.class})
class JdbcFilmRepositoryTests {

    private final JdbcFilmRepository filmRepository;

    @Test
    void testCreateAndFindFilmById() {
        Film film = Film.builder()
                .name("Начало")
                .description("Захватывающий триллер")
                .releaseDate(LocalDate.of(2010, 7, 16))
                .duration(148)
                .mpa(new MPA(1, "G"))
                .build();

        // Создание фильма
        Film createdFilm = filmRepository.create(film);

        // Получение фильма по ID
        Optional<Film> filmOptional = filmRepository.getById(createdFilm.getId());

        // Проверка наличия фильма
        assertTrue(filmOptional.isPresent(), "Film should be present");

        // Получение фильма из Optional
        Film retrievedFilm = filmOptional.get();

        // Проверка полей фильма
        assertEquals(createdFilm.getId(), retrievedFilm.getId(), "Film ID should match");
        assertEquals(film.getName(), retrievedFilm.getName(), "Film name should match");
        assertEquals(film.getDescription(), retrievedFilm.getDescription(), "Film description should match");
        assertEquals(film.getReleaseDate(), retrievedFilm.getReleaseDate(), "Film release date should match");
        assertEquals(film.getDuration(), retrievedFilm.getDuration(), "Film duration should match");
        assertEquals(film.getMpa().getId(), retrievedFilm.getMpa().getId(), "Film MPA ID should match");
        assertEquals(film.getMpa().getName(), retrievedFilm.getMpa().getName(), "Film MPA name should match");
    }

    @Test
    void testGetAllFilms() {
        Film film1 = Film.builder()
                .name("Начало")
                .description("Захватывающий триллер")
                .releaseDate(LocalDate.of(2010, 7, 16))
                .duration(148)
                .mpa(new MPA(1, "PG-13"))
                .build();
        filmRepository.create(film1);

        Film film2 = Film.builder()
                .name("Интерстеллар")
                .description("Путешествие через пространство и время")
                .releaseDate(LocalDate.of(2014, 11, 7))
                .duration(169)
                .mpa(new MPA(1, "PG-13"))
                .build();
        filmRepository.create(film2);

        Collection<Film> films = filmRepository.getAll();
        assertThat(films).hasSize(2);
    }

    @Test
    void testUpdateFilm() {
        Film film = Film.builder()
                .name("Начало")
                .description("Захватывающий триллер")
                .releaseDate(LocalDate.of(2010, 7, 16))
                .duration(148)
                .mpa(new MPA(1, "PG-13"))
                .build();

        Film createdFilm = filmRepository.create(film);

        createdFilm.setName("Начало Обновленное");
        filmRepository.update(createdFilm);

        Optional<Film> filmOptional = filmRepository.getById(createdFilm.getId());
        assertTrue(filmOptional.isPresent());
        assertThat(filmOptional.get().getName()).isEqualTo("Начало Обновленное");
    }
}
