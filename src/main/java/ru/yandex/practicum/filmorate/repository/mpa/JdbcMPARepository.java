package ru.yandex.practicum.filmorate.repository.mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcMPARepository implements MPARepository {
    private final NamedParameterJdbcOperations jdbc;

    private static final String SQL_GET_ALL = "SELECT * FROM MPAS ORDER BY MPA_ID;";

    private static final String SQL_GET_BY_ID = "SELECT * FROM MPAS WHERE MPA_ID = :mpaId";

    @Override
    public Collection<MPA> getAll() {
        return jdbc.query(SQL_GET_ALL, new MPARowMapper());
    }

    @Override
    public Optional<MPA> getById(long mpaId) {
        MapSqlParameterSource params = new MapSqlParameterSource("mpaId", mpaId);
        return jdbc.query(SQL_GET_BY_ID, params, new MPARowMapper()).stream().findFirst();
    }
}
