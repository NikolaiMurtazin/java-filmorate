package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.Update;

import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class User {
    @NotNull(groups = Update.class)
    private Long id;

    @NotBlank(message = "Логин не может быть пустым и содержать пробелы")
    @Pattern(regexp = "^\\S+$")
    private String login;

    private String name;

    @NotBlank(message = "Электронная почта не может быть пустой")
    @Email(message = "Электронная почта должна содержать символ @")
    private String email;

    @NotNull
    @Past(message = "Дата рождения не может быть в будущем.")
    private LocalDate birthday;
}
