package com.timetracker.tracker.dto.req;

import com.timetracker.tracker.dto.validator.PasswordMatches;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import static com.timetracker.tracker.utils.Constants.*;

/**
 * A data transfer object (DTO) class for creating a user.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PasswordMatches(message = PASSWORDS_DO_NOT_MATCH)
public class CreateUserDTO {

    @NotBlank(message = NAME_CANNOT_BE_EMPTY)
    private String name;

    @NotBlank(message = SURNAME_CANNOT_BE_NULL_OR_EMPTY)
    private String surname;

    @NotBlank(message = EMAIL_CANNOT_BE_NULL_OR_EMPTY)
    @Pattern(regexp = REGEXP_EMAIL, message = INVALID_EMAIL_MESSAGE)
    private String email;

    @NotBlank(message = PASSWORD_CANNOT_BE_NULL_OR_EMPTY)
    @Pattern(regexp = PASSWORD_PATTERN, message = INVALID_PASSWORD_MESSAGE)
    private String password;

    @NotBlank(message = PASSWORD_CANNOT_BE_NULL_OR_EMPTY)
    @Pattern(regexp = PASSWORD_PATTERN, message = INVALID_PASSWORD_MESSAGE)
    private String passwordConfirm;

    @NotEmpty(message = THE_ROLE_SET_CANNOT_BE_EMPTY)
    private Set<String> roleNames;

}

