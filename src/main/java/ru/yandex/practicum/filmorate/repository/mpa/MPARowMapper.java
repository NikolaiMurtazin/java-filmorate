package ru.yandex.practicum.filmorate.repository.mpa;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MPARowMapper implements RowMapper<MPA> {
    @Override
    public MPA mapRow(ResultSet rs, int rowNum) throws SQLException {
        return MPA.builder()
                .id(rs.getLong("MPA_ID"))
                .name(rs.getString("NAME"))
                .build();
    }
}
