package com.timetracker.tracker.dto.resp;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.timetracker.tracker.utils.Constants.FIELD_NAME_CANNOT_BE_NULL;
import static com.timetracker.tracker.utils.Constants.MESSAGE_CANNOT_BE_NULL;

/**
 * The class representing a constraint violation in the system.
 * This class is used to store information about a constraint violation such as the field name and the corresponding error message.
 * The field name and message are both required and cannot be null or empty.
 */
@Getter
@RequiredArgsConstructor
public class ConstraintViolation {

    @NotBlank(message = FIELD_NAME_CANNOT_BE_NULL)
    private final String fieldName;

    @NotBlank(message = MESSAGE_CANNOT_BE_NULL)
    private final String message;

}
