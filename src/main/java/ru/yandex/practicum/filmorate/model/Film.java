package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.RealiseDateConstraint;
import ru.yandex.practicum.filmorate.validator.Update;

import java.time.LocalDate;

@Data
@Builder
public class Film {
    @NotNull(groups = Update.class)
    private Long id;

    @NotBlank(message = "Значение не может быть пустым")
    private String name;

    @NotBlank(message = "Описание не может быть пустым")
    @Size(max = 200, message = "Описание фильма не должно превышать 200 символов")
    private String description;

    @NotNull
    @RealiseDateConstraint
    private LocalDate releaseDate;

    @NotNull
    @Positive(message = "Продолжительность фильма должна быть положительной")
    private int duration;
}
