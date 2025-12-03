package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Represents a film director.
 * <p>
 * This entity stores information about a director, including their unique identifier and name.
 * Equality for this class is determined solely by the {@code id} field.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Director {

    /**
     * The unique identifier of the director.
     */
    private Integer id;

    /**
     * The name of the director.
     * <p>
     * This field is mandatory (cannot be blank) and must not exceed 255 characters.
     * </p>
     */
    @NotBlank
    @Size(max = 255)
    private String name;
}