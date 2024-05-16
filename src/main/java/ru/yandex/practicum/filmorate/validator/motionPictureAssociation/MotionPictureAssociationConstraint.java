package ru.yandex.practicum.filmorate.validator.motionPictureAssociation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.yandex.practicum.filmorate.validator.realiseDate.RealiseDateValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = RealiseDateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MotionPictureAssociationConstraint {
    String message() default "Фильм должен содержать возрастной рейтинг.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
