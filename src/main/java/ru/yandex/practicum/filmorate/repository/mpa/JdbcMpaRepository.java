package ru.yandex.practicum.filmorate.repository.mpa;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.repository.BaseJdbcRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * JDBC implementation of the {@link MpaRepository} interface.
 * <p>
 * This repository handles the retrieval of Motion Picture Association (MPA) ratings
 * from the database. It is a read-only repository as MPA ratings are static reference data.
 * </p>
 */
@Repository
public class JdbcMpaRepository extends BaseJdbcRepository<Mpa> implements MpaRepository {

    public JdbcMpaRepository(NamedParameterJdbcOperations jdbc, RowMapper<Mpa> mapper) {
        super(jdbc, mapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Mpa> getById(int mpaId) {
        try {
            String sqlQuery = "SELECT * FROM MPA WHERE MPA_ID = :mpaId";

            return Optional.ofNullable(jdbc.queryForObject(sqlQuery, Map.of("mpaId", mpaId), mapper));
        } catch (EmptyResultDataAccessException ignored) {
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Mpa> getAll() {
        String sqlQuery = "SELECT * FROM MPA";
        return jdbc.query(sqlQuery, mapper);
    }
}