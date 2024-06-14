package ru.yandex.practicum.filmorate.repository.user;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User
                .builder()
                .id(rs.getLong("USER_ID"))
                .login(rs.getString("LOGIN"))
                .name(rs.getString("NAME"))
                .email(rs.getString("EMAIL"))
                .birthday(rs.getDate("BIRTHDAY").toLocalDate())
                .build();
    }
}
