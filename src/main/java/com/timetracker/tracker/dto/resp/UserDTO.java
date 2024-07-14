package com.timetracker.tracker.dto.resp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import static com.timetracker.tracker.utils.Constants.*;

/**
 * A data transfer object (DTO) class representing the user to be displayed.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    @NotNull(message = USER_ID_CANNOT_BE_NULL)
    private Long id;

    @NotBlank(message = NAME_CANNOT_BE_EMPTY)
    private String name;

    @NotBlank(message = SURNAME_CANNOT_BE_NULL_OR_EMPTY)
    private String surname;

    @NotBlank(message = EMAIL_CANNOT_BE_NULL_OR_EMPTY)
    @Pattern(regexp = REGEXP_EMAIL, message = INVALID_EMAIL_MESSAGE)
    private String email;

    @NotEmpty(message = THE_ROLE_SET_CANNOT_BE_EMPTY)
    private Set<String> roles;

}
