package com.timetracker.tracker.services.impl;

import com.timetracker.tracker.entities.RefreshToken;
import com.timetracker.tracker.entities.User;
import com.timetracker.tracker.repositories.RefreshTokenRepository;
import com.timetracker.tracker.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class that provides implementation for generating and regenerating JWT tokens.
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class RefreshTokenServiceImpl implements RefreshTokenService {
    /**
     * RefreshTokenRepository bean.
     *
     * @see com.timetracker.tracker.repositories.RefreshTokenRepository
     */
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * Method to save a new refresh token to database.
     *
     * @param token The refresh token object to save to the database.
     * @param user  The User - token owner.
     * @see com.timetracker.tracker.entities.RefreshToken
     */
    @Override
    public void saveRefreshToken(String token, User user) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .tokenValue(token)
                .build();
        refreshTokenRepository.save(refreshToken);
    }


    /**
     * Method to block all refresh tokens for a given user.
     *
     * @param user The User - the owner of the token.
     * @see com.timetracker.tracker.entities.User
     * @see com.timetracker.tracker.entities.RefreshToken
     */
    @Override
    public void blockUserRefreshTokens(User user) {

    }
}
