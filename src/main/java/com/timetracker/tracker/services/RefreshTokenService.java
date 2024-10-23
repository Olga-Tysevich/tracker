package com.timetracker.tracker.services;

import com.timetracker.tracker.entities.User;

/**
 * The interface for managing JWT refresh tokens.
 */
public interface RefreshTokenService {

    /**
     * Method to save a new refresh token to database.
     *
     * @param token The refresh token object to save to the database.
     * @param user The User - token owner.
     * @see com.timetracker.tracker.entities.RefreshToken
     */
    void saveRefreshToken(String token, User user);

    /**
     * Method to block all refresh tokens for a given user.
     *
     * @param user The User - the owner of the token.
     * @see com.timetracker.tracker.entities.User
     * @see com.timetracker.tracker.entities.RefreshToken
     */
    void removeUserRefreshTokens(User user);

}
