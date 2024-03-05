package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
public class Film {
    private Integer id;
    @NonNull
    @NotEmpty(message = "Значение не может быть пустым")
    private String name;
    @NonNull
    private final String description;
    @NonNull
    private final String releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительной")
    private final int duration;
}
