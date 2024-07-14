package com.timetracker.tracker.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.timetracker.tracker.utils.Constants.EMAIL_CANNOT_BE_NULL_OR_EMPTY;
import static com.timetracker.tracker.utils.Constants.PASSWORD_CANNOT_BE_NULL_OR_EMPTY;

/**
 * Data transfer object representing user login information.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginDTO {

    @NotBlank(message = EMAIL_CANNOT_BE_NULL_OR_EMPTY)
    private String email;

    @NotBlank(message = PASSWORD_CANNOT_BE_NULL_OR_EMPTY)
    private String password;

}
