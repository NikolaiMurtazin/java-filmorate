package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.genre.GenreConstraint;
import ru.yandex.practicum.filmorate.validator.motionPictureAssociation.MotionPictureAssociationConstraint;
import ru.yandex.practicum.filmorate.validator.realiseDate.RealiseDateConstraint;
import ru.yandex.practicum.filmorate.validator.Update;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class Film {
    @NotNull(groups = Update.class)
    private Long id;

    @NotBlank(message = "Значение не может быть пустым")
    private String title;

    @NotBlank(message = "Описание не может быть пустым")
    @Size(max = 200, message = "Описание фильма не должно превышать 200 символов")
    private String description;

    @NotNull
    @RealiseDateConstraint
    private LocalDate releaseDate;

    @NotNull
    @Positive(message = "Продолжительность фильма должна быть положительной")
    private int duration;

    @NotNull
    @GenreConstraint
    private List<String> genre; // ??? Проверить на работоспособность

    @NotNull
    @MotionPictureAssociationConstraint
    private String motionPictureAssociation; // ??? Проверить на работоспособность
}
