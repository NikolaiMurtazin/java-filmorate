package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class Film {
    private Long id;

    @NotBlank(message = "Значение не может быть пустым")
    private String name;

    @NotBlank(message = "Описание не может быть пустым")
    @Size(max = 200, message = "Описание фильма не должно превышать 200 символов")
    private String description;

    @NotNull
    private LocalDate releaseDate;

    @NotNull
    @Positive(message = "Продолжительность фильма должна быть положительной")
    private int duration;

    private Set<Long> likes;

    public void addLike(User user) {
        likes.add(user.getId());
    }

    public void removeLike(User user) {
        likes.remove(user.getId());
    }

    public int sizeLikes() {
        return likes.size();
    }
}
