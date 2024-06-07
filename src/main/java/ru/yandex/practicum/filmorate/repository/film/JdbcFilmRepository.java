package ru.yandex.practicum.filmorate.repository.film;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.genre.GenreRowMapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JdbcFilmRepository implements FilmRepository {
    private final NamedParameterJdbcOperations jdbc;

    private static final String SQL_GET_ALL = """
        SELECT f.*, m.NAME
        FROM FILMS AS f
        JOIN MPAS AS m ON f.MPA_ID = m.MPA_ID
        """;

    private static final String SQL_GET_BY_ID = """
        SELECT
        f.*, m.NAME
        FROM FILMS f
        JOIN MPAS m ON f.MPA_ID = m.MPA_ID
//        LEFT JOIN FILM_GENRES fg ON f.FILM_ID = fg.FILM_ID
//        LEFT JOIN GENRES g ON fg.GENRE_ID = g.GENRE_ID
        WHERE f.FILM_ID = :filmId
        """;

    private static final String SQL_INSERT = """
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

    private static final String SQL_SELECT_POPULAR = """
        SELECT
            f.*, m.NAME as MPA_NAME
        FROM FILMS f
        JOIN MPAS m ON f.MPA_ID = m.MPA_ID
        LEFT JOIN FILM_LIKES fl ON f.FILM_ID = fl.FILM_ID
        GROUP BY f.FILM_ID
        ORDER BY COUNT(fl.USER_ID) DESC
        LIMIT :count
        """;

    private static final String SQL_INSERT_FILM_GENRES = """
        INSERT INTO FILM_GENRES (FILM_ID, GENRE_ID)
        VALUES (:filmId, :genreId)
        """;

    private static final String SQL_DELETE_FILM_GENRES = """
        DELETE FROM FILM_GENRES
        WHERE FILM_ID = :filmId
        """;

    private static final String SQL_SELECT_GENRES_BY_FILM_ID = """
        SELECT g.*
        FROM GENRES g
        JOIN FILM_GENRES fg ON g.GENRE_ID = fg.GENRE_ID
        WHERE fg.FILM_ID = :filmId
        """;

    private Film mapFilmWithGenres(Film film) {
        MapSqlParameterSource params = new MapSqlParameterSource("filmId", film.getId());
        List<Genre> genres = jdbc.query(SQL_SELECT_GENRES_BY_FILM_ID, params, new GenreRowMapper());
        film.setGenres(new HashSet<>(genres));
        return film;
    }

    private void saveGenres(Film film) {
        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            List<MapSqlParameterSource> batchValues = film.getGenres().stream()
                    .map(genre -> new MapSqlParameterSource()
                            .addValue("filmId", film.getId())
                            .addValue("genreId", genre.getId()))
                    .collect(Collectors.toList());
            jdbc.batchUpdate(SQL_INSERT_FILM_GENRES, batchValues.toArray(new MapSqlParameterSource[0]));
        }
    }

    @Override
    public Collection<Film> getAll() {
        List<Film> films = jdbc.query(SQL_GET_ALL, new FilmRowMapper());
        return films.stream()
                .map(this::mapFilmWithGenres)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Film> getById(long filmId) {
        MapSqlParameterSource params = new MapSqlParameterSource("filmId", filmId);
        Film film = jdbc.query(SQL_GET_BY_ID, params, new FilmResultSetExtractor());
        if (film == null) {
            return Optional.empty();
        }
        return Optional.of(mapFilmWithGenres(film));
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
        jdbc.update(SQL_INSERT, params, keyHolder, new String[]{"FILM_ID"});
        long filmId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        film.setId(filmId);

        saveGenres(film);

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

        jdbc.update(SQL_DELETE_FILM_GENRES, new MapSqlParameterSource("filmId", film.getId()));
        saveGenres(film);

        return film;
    }

    @Override
    public Collection<Film> getPopularFilms(int count) {
        MapSqlParameterSource params = new MapSqlParameterSource("count", count);
        List<Film> films = jdbc.query(SQL_SELECT_POPULAR, params, new FilmRowMapper());
        return films.stream()
                .map(this::mapFilmWithGenres)
                .collect(Collectors.toList());
    }
}
