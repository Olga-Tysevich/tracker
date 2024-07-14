package com.timetracker.tracker.dto.validator;


import com.timetracker.tracker.dto.req.CreateUserDTO;
import com.timetracker.tracker.dto.req.UpdateUserDTO;
import com.timetracker.tracker.exceptions.UnsupportedDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

/**
 * A validator class for checking if the password and password confirmation match in a user DTO object.
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    /**
     * Initializes the validator.
     *
     * @param constraintAnnotation annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    /**
     * Validates if the password and password confirmation match in the user DTO object.
     *
     * @param obj     the user DTO object.
     * @param context context in which the constraint is evaluated.
     * @return true if the passwords match, false otherwise.
     * @throws UnsupportedDTO if the DTO object is not supported.
     */
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj instanceof CreateUserDTO userDTO) {
            if (Objects.nonNull(userDTO.getPassword())) {
                return userDTO.getPassword().equals(userDTO.getPasswordConfirm());
            }
        } else if (obj instanceof UpdateUserDTO userDTO) {
            if (Objects.nonNull(userDTO.getPassword())) {
                return userDTO.getPassword().equals(userDTO.getPasswordConfirm());
            }
        }
        throw new UnsupportedDTO();
    }
}