package com.timetracker.tracker.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A data transfer object (DTO) class representing a logged-in user.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoggedUserDTO {
    /**
     * The type of authentication token (e.g., Bearer).
     */
    @Builder.Default
    private String type = "Bearer";
    /**
     * The access token for the logged-in user.
     */
    private String accessToken;
    /**
     * The refresh token for the logged-in user.
     */
    private String refreshToken;
    /**
     * The user details associated with the logged-in user.
     */
    private UserDTO userDTO;

}
