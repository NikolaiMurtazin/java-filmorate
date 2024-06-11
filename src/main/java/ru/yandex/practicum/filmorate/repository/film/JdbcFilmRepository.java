package ru.yandex.practicum.filmorate.repository.film;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JdbcFilmRepository implements FilmRepository {
    private final NamedParameterJdbcOperations jdbc;

    private static final String GET_ALL = """
        SELECT f.*, m.*
        FROM FILMS f
        LEFT JOIN MPAS m ON f.mpa_id = m.mpa_id
    """;

    private static final String SQL_GET_BY_ID = """
        SELECT f.*, m.*
        FROM FILMS f
        LEFT JOIN MPAS m ON f.MPA_ID = m.MPA_ID
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
        SELECT f.*, m.*
        FROM FILMS f
        LEFT JOIN FILM_LIKES fl ON f.FILM_ID = fl.FILM_ID
        LEFT JOIN MPAS m ON f.MPA_ID = m.MPA_ID
        GROUP BY f.FILM_ID
        ORDER BY COUNT(fl.USER_ID) DESC
        LIMIT :count;
    """;

    private static final String SQL_GET_GENRES_FOR_FILMS = "SELECT fg.FILM_ID, g.GENRE_ID, g.NAME FROM FILM_GENRES fg " +
            "JOIN GENRES g ON fg.GENRE_ID = g.GENRE_ID " +
            "WHERE fg.FILM_ID IN (:filmIds)";

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

    private Map<Long, Set<Genre>> getGenresForFilms(List<Long> filmIds) {
        if (filmIds.isEmpty()) {
            return Collections.emptyMap();
        }

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("filmIds", filmIds);

        List<Map<String, Object>> rows = jdbc.queryForList(SQL_GET_GENRES_FOR_FILMS, params);
        Map<Long, Set<Genre>> genresMap = new HashMap<>();

        for (Map<String, Object> row : rows) {
            Long filmId = (Long) row.get("FILM_ID");
            Genre genre = Genre.builder()
                    .id((Integer) row.get("GENRE_ID"))
                    .name((String) row.get("NAME"))
                    .build();

            genresMap.computeIfAbsent(filmId, k -> new HashSet<>()).add(genre);
        }

        return genresMap;
    }

    @Override
    public Collection<Film> getAll() {
        List<Film> films = jdbc.query(GET_ALL, new FilmResultSetExtractor());
        List<Long> filmIds = Objects.requireNonNull(films).stream().map(Film::getId).collect(Collectors.toList());
        Map<Long, Set<Genre>> genres = getGenresForFilms(filmIds);

        for (Film film : films) {
            film.setGenres(genres.get(film.getId()));
        }

        return films;
    }

    @Override
    public Optional<Film> getById(long filmId) {
        MapSqlParameterSource params = new MapSqlParameterSource("filmId", filmId);
        List<Film> films = jdbc.query(SQL_GET_BY_ID, params, new FilmResultSetExtractor());

        if (Objects.requireNonNull(films).isEmpty()) {
            return Optional.empty();
        }

        Film film = films.getFirst();
        Map<Long, Set<Genre>> genres = getGenresForFilms(Collections.singletonList(filmId));
        film.setGenres(genres.get(filmId));

        return Optional.of(film);
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

        List<Film> films = jdbc.query(SQL_GET_POPULAR_FILMS, params, new FilmResultSetExtractor());
        List<Long> filmIds = Objects.requireNonNull(films).stream().map(Film::getId).collect(Collectors.toList());
        Map<Long, Set<Genre>> genres = getGenresForFilms(filmIds);

        films.forEach(film -> film.setGenres(genres.getOrDefault(film.getId(), new HashSet<>())));

        return films;
    }
}
