package ru.yandex.practicum.filmorate.repository.film;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcFilmRepository implements FilmRepository {
    private final NamedParameterJdbcOperations jdbc;

    private static final String GET_ALL = """
        SELECT f.*, mpa.*, g.*
        FROM films f
        LEFT JOIN MPAS mpa ON f.mpa_id = mpa.mpa_id
        LEFT JOIN FILM_GENRES fg ON f.film_id = fg.film_id
        LEFT JOIN GENRES g ON fg.genre_id = g.genre_id
    """;

    private static final String SQL_GET_BY_ID = """
        SELECT
        f.*, m.*, g.*
        FROM FILMS f
        LEFT JOIN MPAS m ON f.MPA_ID = m.MPA_ID
        LEFT JOIN FILM_GENRES fg ON f.FILM_ID = fg.FILM_ID
        LEFT JOIN GENRES g ON fg.GENRE_ID = g.GENRE_ID
        WHERE f.FILM_ID = :filmId
        """;

    private static final String SQL_CREAT = """
        INSERT INTO FILMS
            (NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID)
        VALUES
            (:name, :description, :releaseDate, :duration, :mpaId)
        """;

    private static final String SQL_UPDATE = """
        UPDATE FILMS
        SET
            NAME = :name,
            DESCRIPTION = :description,
            RELEASE_DATE = :releaseDate,
            DURATION = :duration,
            MPA_ID = :mpaId
        WHERE
            FILM_ID = :filmId
        """;

    private static final String SQL_GET_POPULAR_FILMS = """
        SELECT f.*, m.*, g.*, COUNT(fl.USER_ID) as like_count
        FROM films f
        LEFT JOIN film_likes fl ON f.FILM_ID = fl.FILM_ID
        LEFT JOIN mpas m ON f.MPA_ID = m.MPA_ID
        LEFT JOIN film_genres fg ON f.FILM_ID = fg.FILM_ID
        LEFT JOIN genres g ON fg.GENRE_ID = g.GENRE_ID
        GROUP BY f.FILM_ID, m.MPA_ID, g.GENRE_ID
        ORDER BY like_count DESC, f.FILM_ID
        LIMIT :count;
    """;

    private static final String SQL_INSERT_FILM_GENRES = "INSERT INTO FILM_GENRES (FILM_ID, GENRE_ID) " +
            "VALUES (:filmId, :genreId)";

    private static final String SQL_DELETE_FILM_GENRES = "DELETE FROM FILM_GENRES WHERE FILM_ID = :filmId";

    private void addGenresToFilm(Film film) {
        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            MapSqlParameterSource[] batchParams = film.getGenres().stream()
                    .map(genre -> new MapSqlParameterSource()
                            .addValue("filmId", film.getId())
                            .addValue("genreId", genre.getId()))
                    .toArray(MapSqlParameterSource[]::new);
            jdbc.batchUpdate(SQL_INSERT_FILM_GENRES, batchParams);
        }
    }

    private void deleteGenresFromFilm(long filmId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("filmId", filmId);
        jdbc.update(SQL_DELETE_FILM_GENRES, params);
    }

    @Override
    public Collection<Film> getAll() {
        return jdbc.query(GET_ALL, new FilmResultSetExtractor());
    }

    @Override
    public Optional<Film> getById(long filmId) {
        MapSqlParameterSource params = new MapSqlParameterSource("filmId", filmId);
        return Objects.requireNonNull(jdbc.query(SQL_GET_BY_ID, params, new FilmResultSetExtractor())).stream().findFirst();
    }

    @Override
    public Film create(Film film) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", film.getName())
                .addValue("description", film.getDescription())
                .addValue("releaseDate", film.getReleaseDate())
                .addValue("duration", film.getDuration())
                .addValue("mpaId", film.getMpa().getId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(SQL_CREAT, params, keyHolder, new String[]{"FILM_ID"});
        long filmId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        film.setId(filmId);

        addGenresToFilm(film);

        return film;
    }

    @Override
    public Film update(Film film) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", film.getName())
                .addValue("description", film.getDescription())
                .addValue("releaseDate", film.getReleaseDate())
                .addValue("duration", film.getDuration())
                .addValue("mpaId", film.getMpa().getId())
                .addValue("filmId", film.getId());
        jdbc.update(SQL_UPDATE, params);

        deleteGenresFromFilm(film.getId());
        addGenresToFilm(film);

        return film;
    }

    @Override
    public Collection<Film> getPopularFilms(int count) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("count", count);

        return jdbc.query(SQL_GET_POPULAR_FILMS, params, new FilmResultSetExtractor());
    }
}
