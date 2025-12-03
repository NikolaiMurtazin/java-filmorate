package ru.yandex.practicum.filmorate.repository.genre;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.BaseJdbcRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * JDBC implementation of the {@link GenreRepository} interface.
 * <p>
 * This repository handles read-only operations for {@link Genre} entities using named JDBC parameters.
 * </p>
 */
@Repository
public class JdbcGenreRepository extends BaseJdbcRepository<Genre> implements GenreRepository {

    public JdbcGenreRepository(NamedParameterJdbcOperations jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Genre> getById(int genreId) {
        try {
            String sqlQuery = "SELECT * FROM GENRES WHERE GENRE_ID = :genreId";
            return Optional.ofNullable(jdbc.queryForObject(sqlQuery, Map.of("genreId", genreId), mapper));
        } catch (EmptyResultDataAccessException ignored) {
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Genre> getAll() {
        String sqlQuery = "SELECT * FROM GENRES";
        return jdbc.query(sqlQuery, mapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countMatchingGenres(List<Integer> genreIds) {
        if (genreIds == null || genreIds.isEmpty()) {
            return 0;
        }

        String sqlQuery = "SELECT COUNT(*) FROM GENRES WHERE GENRE_ID IN (:genreIds)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("genreIds", genreIds);

        Integer count = jdbc.queryForObject(sqlQuery, params, Integer.class);
        return count != null ? count : 0;
    }
}