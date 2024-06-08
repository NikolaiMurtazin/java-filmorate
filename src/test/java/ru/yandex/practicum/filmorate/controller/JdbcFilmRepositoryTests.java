package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.repository.film.JdbcFilmRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({JdbcFilmRepository.class})
class JdbcFilmRepositoryTests {

    private final JdbcFilmRepository filmRepository;

    @Test
    void testCreateAndFindFilmById() {
        Film film = Film.builder()
                .title("Начало")
                .description("Захватывающий триллер")
                .releaseDate(LocalDate.of(2010, 7, 16))
                .duration(148)
                .mpa(new MPA(1L, "PG-13"))
                .build();

        Film createdFilm = filmRepository.create(film);

        Optional<Film> filmOptional = filmRepository.getById(createdFilm.getId());

        assertTrue(filmOptional.isPresent());
        assertThat(filmOptional).contains(createdFilm);
    }

    @Test
    void testGetAllFilms() {
        Film film1 = Film.builder()
                .title("Начало")
                .description("Захватывающий триллер")
                .releaseDate(LocalDate.of(2010, 7, 16))
                .duration(148)
                .mpa(new MPA(1L, "PG-13"))
                .build();
        filmRepository.create(film1);

        Film film2 = Film.builder()
                .title("Интерстеллар")
                .description("Путешествие через пространство и время")
                .releaseDate(LocalDate.of(2014, 11, 7))
                .duration(169)
                .mpa(new MPA(1L, "PG-13"))
                .build();
        filmRepository.create(film2);

        Collection<Film> films = filmRepository.getAll();
        assertThat(films).hasSize(2);
    }

    @Test
    void testUpdateFilm() {
        Film film = Film.builder()
                .title("Начало")
                .description("Захватывающий триллер")
                .releaseDate(LocalDate.of(2010, 7, 16))
                .duration(148)
                .mpa(new MPA(1L, "PG-13"))
                .build();

        Film createdFilm = filmRepository.create(film);

        createdFilm.setTitle("Начало Обновленное");
        filmRepository.update(createdFilm);

        Optional<Film> filmOptional = filmRepository.getById(createdFilm.getId());
        assertTrue(filmOptional.isPresent());
        assertThat(filmOptional.get().getTitle()).isEqualTo("Начало Обновленное");
    }
}