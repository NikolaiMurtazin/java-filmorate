package ru.yandex.practicum.filmorate.validator.motionPictureAssociation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class MotionPictureAssociationValidator implements ConstraintValidator<MotionPictureAssociationConstraint, String> {
    List<String> listMotionPictureAssociation = Arrays.asList("G", "PG", "PG-13", "R", "NC-17");
    @Override
    public boolean isValid(String strings, ConstraintValidatorContext constraintValidatorContext) {
        if (strings == null) {
            return true;
        }

        for (String s : listMotionPictureAssociation) {
            if(strings.equals(s)) {
                return false;
            }
        }

        return true;
    }
}
