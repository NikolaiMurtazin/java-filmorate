package ru.yandex.practicum.filmorate.validator.genre;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class GenreValidator implements ConstraintValidator<GenreConstraint, List<String>> {
    List<String> listGenre = Arrays.asList("Комедия", "Драма", "Мультфильм", "Триллер", "Документальный", "Боевик");

    @Override
    public boolean isValid(List<String> list, ConstraintValidatorContext constraintValidatorContext) {
        if (list == null) {
            return true;
        }

        boolean isV = false;
        for (String s : list) {
            for (String str : listGenre) {
                if (!s.equals(str)) {
                    isV = true;
                    break;
                }
            }
        }

        return isV;
    }
}
