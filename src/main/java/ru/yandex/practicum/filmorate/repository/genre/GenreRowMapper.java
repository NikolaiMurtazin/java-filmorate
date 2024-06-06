package ru.yandex.practicum.filmorate.repository.genre;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreRowMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Genre
                .builder()
                .id(rs.getLong("GENRE_ID"))
                .name(rs.getString("NAME"))
                .build();
    }
}
