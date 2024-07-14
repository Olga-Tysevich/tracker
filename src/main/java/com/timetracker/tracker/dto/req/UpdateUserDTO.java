package com.timetracker.tracker.dto.req;

import com.timetracker.tracker.dto.validator.PasswordMatches;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import static com.timetracker.tracker.utils.Constants.USER_ID_CANNOT_BE_NULL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PasswordMatches
public class UpdateUserDTO {

    @NotNull(message = USER_ID_CANNOT_BE_NULL)
    private Long id;

    /**
     * The username for updating a user. May be null.
     */
    private String name;

    /**
     * The user surname for updating a user. May be null.
     */
    private String surname;

    /**
     * The password for updating a user. May be null.
     * If not zero, then the passwordConfirm field must be filled in.
     */
    private String password;

    /**
     * The password confirmation field for updating a user. May be null.
     */
    private String passwordConfirm;
    /**
     * The role set for updating a user. May be null or empty.
     */
    private Set<String> roleNames;
}
