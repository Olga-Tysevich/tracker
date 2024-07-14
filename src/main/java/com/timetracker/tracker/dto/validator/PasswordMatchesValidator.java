package com.timetracker.tracker.dto.validator;


import com.timetracker.tracker.dto.req.CreateUserDTO;
import com.timetracker.tracker.dto.req.UpdateUserDTO;
import com.timetracker.tracker.exceptions.UnsupportedDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

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