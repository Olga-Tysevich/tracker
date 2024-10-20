package com.timetracker.tracker.facades;

import com.timetracker.tracker.dto.req.UserLoginDTO;
import com.timetracker.tracker.dto.resp.LoggedUserDTO;

/**
 * This interface provides a simplified interface for managing user authentication. *
 */
public interface AuthFacade {

    /**
     * Authenticates user with provided credentials and generates JWT tokens.
     *
     * @param req The UserLoginDTO containing user login credentials.
     * @return The LoggedUserDTO with JWT tokens.
     * @see com.timetracker.tracker.dto.req.UserLoginDTO
     * @see com.timetracker.tracker.dto.resp.LoggedUserDTO
     */
    LoggedUserDTO loginUser(UserLoginDTO req);

    /**
     * Regenerates JWT tokens using a refresh token.
     *
     * @param refreshToken The refresh token for re-logging in the user.
     * @return The LoggedUserDTO with new JWT tokens.
     * @see com.timetracker.tracker.dto.resp.LoggedUserDTO
     */
    LoggedUserDTO reLoginUser(String refreshToken);
}
