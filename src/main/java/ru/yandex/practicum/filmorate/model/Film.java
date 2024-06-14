package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.validator.Update;
import ru.yandex.practicum.filmorate.validator.realiseDate.RealiseDateConstraint;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private static final int FILM_DESCRIPTION_MAX_LENGTH = 200;

    @NotNull(groups = Update.class)
    private Long id;

    @NotBlank(message = "Название не может быть пустым")
    private String name;

    @NotBlank(message = "Описание не может быть пустым")
    @Size(max = FILM_DESCRIPTION_MAX_LENGTH, message = "Описание фильма не должно превышать 200 символов")
    private String description;

    @NotNull
    @RealiseDateConstraint
    private LocalDate releaseDate;

    @NotNull
    @Positive(message = "Продолжительность фильма должна быть положительной")
    private int duration;

    private MPA mpa;

    private Set<Genre> genres = new LinkedHashSet<>();
}
