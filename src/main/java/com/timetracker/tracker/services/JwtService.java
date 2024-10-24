package com.timetracker.tracker.services;

import com.timetracker.tracker.entities.User;
import io.jsonwebtoken.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

/**
 * This class provides methods for generating access tokens and refresh tokens.
 */
@Component
public interface JwtService {

    /**
     * Generates JWT access token.
     *
     * @param user represents a user object.
     * @return String representation of JWT access token.
     * @see User
     */
    String generateAccessToken(@NotNull User user);

    /**
     * Generates JWT refresh token.
     *
     * @param user represents a user object.
     * @return String representation of JWT refresh token.
     * @see User
     */
    String generateRefreshToken(@NotNull User user) ;

    /**
     * Validates JWT access token.
     *
     * @param accessToken String representation of JWT access token.
     * @return true if the JWT access token is valid, false otherwise.
     */
     boolean validateAccessToken(@NotNull String accessToken) ;

    /**
     * Validates JWT refresh token
     *
     * @param refreshToken String representation of JWT refresh token.
     * @return true if the JWT refresh token is valid, false otherwise.
     */
     boolean validateRefreshToken(@NotNull String refreshToken) ;


    /**
     * Extracts claim from JWT access token.
     *
     * @param token String representation of JWT access token.
     * @return The claims contained in the JWT access token.
     */
     Claims getAccessClaims(@NotNull String token);

    /**
     * Extracts claim from JWT refresh token.
     *
     * @param token String representation of JWT refresh token.
     * @return The claims contained in the JWT refresh token.
     */
     Claims getRefreshClaims(@NotNull String token);

}
