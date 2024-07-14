package com.timetracker.tracker.services;

import com.timetracker.tracker.dto.resp.LoggedUserDTO;
import com.timetracker.tracker.entities.User;

/**
 * The interface for managing JWT tokens.
 */
public interface JwtService {

    /**
     * Generates a pair of JWT tokens (access and refresh tokens) for the specified user.
     *
     * @param user The user for whom the tokens are generated.
     * @return A Data Transfer Object (DTO) that contains the generated tokens.
     * @see com.timetracker.tracker.entities.User
     * @see com.timetracker.tracker.dto.resp.LoggedUserDTO
     */
    LoggedUserDTO generatePairOfTokens(User user);

    /**
     * Regenerates a pair of JWT tokens (access and refresh tokens) based on the provided refresh token.
     *
     * @param refreshToken The refresh token used to regenerate the tokens.
     * @return A Data Transfer Object (DTO) that contains the regenerated tokens.
     * @see com.timetracker.tracker.dto.resp.LoggedUserDTO
     */
    LoggedUserDTO regeneratePairOfTokens(String refreshToken);
}
