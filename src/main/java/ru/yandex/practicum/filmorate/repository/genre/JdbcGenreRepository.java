package ru.yandex.practicum.filmorate.repository.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcGenreRepository implements GenreRepository {
    private final NamedParameterJdbcOperations jdbc;
    private final GenreRowMapper genreRowMapper = new GenreRowMapper();

    private static final String SQL_GET_ALL = """
        SELECT
            GENRE_ID,
            NAME
        FROM
            GENRES
        """;

    private static final String SQL_GET_BY_ID = """
        SELECT
            GENRE_ID,
            NAME
        FROM
            GENRES
        WHERE
            GENRE_ID = :genreId
        """;

    @Override
    public Collection<Genre> getAll() {
        return jdbc.query(SQL_GET_ALL, genreRowMapper);
    }

    @Override
    public Optional<Genre> getById(long genreId) {
        MapSqlParameterSource params = new MapSqlParameterSource("genreId", genreId);
        return jdbc.query(SQL_GET_BY_ID, params, genreRowMapper).stream().findFirst();
    }
}
