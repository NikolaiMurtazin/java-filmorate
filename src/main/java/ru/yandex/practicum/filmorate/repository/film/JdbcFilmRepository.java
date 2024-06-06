package ru.yandex.practicum.filmorate.repository.film;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcFilmRepository implements FilmRepository {
    private final NamedParameterJdbcOperations jdbc;
    private final FilmRowMapper filmRowMapper = new FilmRowMapper();

    private static final String SQL_GET_ALL = """
        SELECT
            FILM_ID,
            TITLE,
            DESCRIPTION,
            RELEASE_DATE,
            DURATION,
            MPA_ID
        FROM
            FILMS
        """;

    private static final String SQL_GET_BY_ID = """
        SELECT
            FILM_ID,
            TITLE,
            DESCRIPTION,
            RELEASE_DATE,
            DURATION,
            MPA_ID
        FROM
            FILMS
        WHERE
            FILM_ID = :filmId
        """;

    private static final String SQL_CREATE = """
        INSERT INTO FILMS
            (TITLE, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID)
        VALUES
            (:title, :description, :releaseDate, :duration, :mpaId)
        """;

    private static final String SQL_UPDATE = """
        UPDATE FILMS
        SET
            TITLE = :title,
            DESCRIPTION = :description,
            RELEASE_DATE = :releaseDate,
            DURATION = :duration,
            MPA_ID = :mpaId
        WHERE
            FILM_ID = :filmId
        """;

    private static final String SQL_GET_POPULAR_FILMS = """
        SELECT
            FILMS.FILM_ID,
            FILMS.TITLE,
            FILMS.DESCRIPTION,
            FILMS.RELEASE_DATE,
            FILMS.DURATION,
            FILMS.MPA_ID,
            COUNT(FILM_LIKES.USER_ID) AS LIKE_COUNT
        FROM
            FILMS
            LEFT JOIN FILM_LIKES ON FILMS.FILM_ID = FILM_LIKES.FILM_ID
        GROUP BY
            FILMS.FILM_ID
        ORDER BY
            LIKE_COUNT DESC
        LIMIT :count
        """;

    @Override
    public Collection<Film> getAll() {
        return jdbc.query(SQL_GET_ALL, filmRowMapper);
    }

    @Override
    public Optional<Film> getById(long filmId) {
        MapSqlParameterSource params = new MapSqlParameterSource("filmId", filmId);
        return jdbc.query(SQL_GET_BY_ID, params, filmRowMapper).stream().findFirst();
    }

    @Override
    public Film create(Film film) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", film.getTitle())
                .addValue("description", film.getDescription())
                .addValue("releaseDate", film.getReleaseDate())
                .addValue("duration", film.getDuration())
                .addValue("mpaId", film.getMpa().getId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(SQL_CREATE, params, keyHolder, new String[]{"FILM_ID"});
        long filmId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        film.setId(filmId);
        return film;
    }

    @Override
    public Film update(Film film) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", film.getTitle())
                .addValue("description", film.getDescription())
                .addValue("releaseDate", film.getReleaseDate())
                .addValue("duration", film.getDuration())
                .addValue("mpaId", film.getMpa().getId())
                .addValue("filmId", film.getId());
        jdbc.update(SQL_UPDATE, params);
        return film;
    }

    @Override
    public Collection<Film> getPopularFilms(int count) {
        MapSqlParameterSource params = new MapSqlParameterSource("count", count);
        return jdbc.query(SQL_GET_POPULAR_FILMS, params, filmRowMapper);
    }
}
