package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

/**
 * Abstract base class for JDBC-based repositories.
 * <p>
 * This class provides common infrastructure for repositories, holding references to
 * {@link NamedParameterJdbcOperations} and a specific {@link RowMapper}.
 * Subclasses should extend this to implement entity-specific data access logic,
 * reducing boilerplate code for dependency injection.
 * </p>
 *
 * @param <T> the type of the entity managed by this repository
 */
@RequiredArgsConstructor
public abstract class BaseJdbcRepository<T> {

    /**
     * The JDBC operations helper supporting named parameters (e.g., :id instead of ?).
     * <p>
     * Provides methods for querying and updating the database using named parameters,
     * which improves readability and maintainability of SQL queries.
     * </p>
     */
    protected final NamedParameterJdbcOperations jdbc;

    /**
     * The mapper used to convert SQL result set rows into entity objects of type {@code T}.
     */
    protected final RowMapper<T> mapper;
}
