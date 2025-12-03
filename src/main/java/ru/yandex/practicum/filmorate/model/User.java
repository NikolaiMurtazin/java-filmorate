package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Represents a user of the application.
 * <p>
 * This entity stores personal details such as email, login, name, and birth date.
 * It serves as the core identity for interactions within the platform (friends, likes, reviews).
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * The unique identifier of the user.
     */
    private Long id;

    /**
     * The user's email address.
     * <p>
     * Must be a valid email format (containing '@') and not exceed 255 characters.
     * This field is mandatory.
     * </p>
     */
    @NotBlank
    @Email
    @Size(max = 255)
    private String email;

    /**
     * The user's system login.
     * <p>
     * Must be unique and cannot contain spaces.
     * This field is mandatory and limited to 100 characters.
     * </p>
     */
    @NotBlank
    @Size(max = 100)
    private String login;

    /**
     * The display name of the user.
     * <p>
     * If left blank during creation, the business logic typically defaults this to the {@code login}.
     * </p>
     */
    @Size(max = 255)
    private String name;

    /**
     * The user's date of birth.
     * <p>
     * Must be a date in the past or the present (cannot be in the future).
     * </p>
     */
    @PastOrPresent
    private LocalDate birthday;
}