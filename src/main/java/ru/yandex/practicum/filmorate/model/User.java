package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class User {
    private Long userId;

    @NotBlank(message = "Электронная почта не может быть пустой")
    @Email(message = "Электронная почта должна содержать символ @")
    private String email;

    @NotBlank(message = "Логин не может быть пустым и содержать пробелы")
    private String login;

    private String name;

    @NotNull
    @Past(message = "Дата рождения не может быть в будущем.")
    private LocalDate birthday;

    private Set<Long> friends;

    public void addFriend(User friend) {
        friends.add(friend.getUserId());
    }

    public void removeFriend(User friend) {
        friends.remove(friend.getUserId());
    }

    private Set<Long> likeFilms;

    public void addFilm(Film film) {
        likeFilms.add(film.getFilmId());
    }

    public void removeFilm(Film film) {
        likeFilms.remove(film.getFilmId());
    }


}
