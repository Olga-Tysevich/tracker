package com.timetracker.tracker.services.impl;

import com.timetracker.tracker.conf.JwtProvider;
import com.timetracker.tracker.dto.resp.LoggedUserDTO;
import com.timetracker.tracker.entities.RefreshToken;
import com.timetracker.tracker.entities.User;
import com.timetracker.tracker.exceptions.InvalidRefreshTokenException;
import com.timetracker.tracker.exceptions.UserNotFoundException;
import com.timetracker.tracker.mappers.UserMapper;
import com.timetracker.tracker.repositories.RefreshTokenRepository;
import com.timetracker.tracker.repositories.UserRepository;
import com.timetracker.tracker.services.JwtService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.timetracker.tracker.utils.Constants.TOKEN_CANNOT_BE_NULL_OR_EMPTY;

/**
 * Service class that provides implementation for generating and regenerating JWT tokens.
 */
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    /**
     * RefreshTokenRepository bean.
     *
     * @see com.timetracker.tracker.repositories.RefreshTokenRepository
     */
    private final RefreshTokenRepository refreshTokenRepository;
    /**
     * JwtProvider bean.
     *
     * @see com.timetracker.tracker.conf.JwtProvider
     */
    private final JwtProvider jwtProvider;
    /**
     * UserRepository bean.
     *
     * @see com.timetracker.tracker.repositories.UserRepository
     */
    private final UserRepository userRepository;

    /**
     * Generates a pair of access and refresh tokens for the given user.
     *
     * @param user The user for whom tokens are generated.
     * @return LoggedUserDTO containing the access token, refresh token, and user details.
     */
    @Override
    public LoggedUserDTO generatePairOfTokens(User user) {
        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);
        saveRefreshToken(user.getEmail(), refreshToken);
        return LoggedUserDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userDTO(UserMapper.INSTANCE.toDTO(user))
                .build();
    }

    /**
     * Regenerates a pair of access and refresh tokens using the provided refresh token.
     *
     * @param refreshToken The refresh token used for token regeneration.
     * @return LoggedUserDTO containing the new access token, refresh token, and user details.
     */
    @Override
    public LoggedUserDTO regeneratePairOfTokens(@Valid @NotBlank(message = TOKEN_CANNOT_BE_NULL_OR_EMPTY)
                                                String refreshToken) {
        if (!jwtProvider.validateRefreshToken(refreshToken)) {
            throw new InvalidRefreshTokenException();
        }
        String email = jwtProvider.getRefreshClaims(refreshToken).getSubject();
        User user = userRepository.getByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        return generatePairOfTokens(user);
    }

    /**
     * Saves the refresh token for the given email.
     *
     * @param email The email of the user for whom the token is saved.
     * @param token The refresh token to be saved.
     */
    private void saveRefreshToken(@Valid @Email String email,
                                  @Valid @NotBlank(message = TOKEN_CANNOT_BE_NULL_OR_EMPTY) String token) {
        if (!userRepository.existsByEmail(email)) {
            throw new UserNotFoundException();
        }
        refreshTokenRepository.save(new RefreshToken(email, token));
    }

}
