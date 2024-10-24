package com.timetracker.tracker.facades.impl;

import com.timetracker.tracker.services.JwtService;
import com.timetracker.tracker.dto.req.UserLoginDTO;
import com.timetracker.tracker.dto.resp.LoggedUserDTO;
import com.timetracker.tracker.entities.User;
import com.timetracker.tracker.exceptions.InvalidRefreshTokenException;
import com.timetracker.tracker.exceptions.PasswordMismatchException;
import com.timetracker.tracker.facades.AuthFacade;
import com.timetracker.tracker.mappers.UserMapper;
import com.timetracker.tracker.services.RefreshTokenService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.timetracker.tracker.utils.Constants.TOKEN_CANNOT_BE_NULL_OR_EMPTY;

/**
 * This class implements the AuthFacade interface and contains for managing user authentication.
 *
 * @see com.timetracker.tracker.facades.AuthFacade
 **/
@RequiredArgsConstructor
@Component
public class AuthFacadeImpl implements AuthFacade {
    /**
     * JwtAuthService bean.
     *
     * @see RefreshTokenService
     */
    private final RefreshTokenService refreshTokenService;
    /**
     * AuthenticationManager bean.
     *
     * @see com.timetracker.tracker.conf.WebSecurityConfig
     */
    private final AuthenticationManager authenticationManager;
    /**
     * UserDetailsService bean.
     *
     * @see com.timetracker.tracker.services.impl.UserDetailsServiceImpl
     */
    private final UserDetailsService userDetailsService;
    /**
     * PasswordEncoder bean.
     *
     * @see com.timetracker.tracker.conf.WebSecurityConfig
     */
    private final PasswordEncoder passwordEncoder;
    /**
     * JwtProvider bean.
     *
     * @see JwtService
     */
    private final JwtService jwtService;

    /**
     * Authenticates user with provided credentials and generates JWT tokens.
     *
     * @param req The UserLoginDTO containing user login credentials.
     * @return The LoggedUserDTO with JWT tokens.
     * @see com.timetracker.tracker.dto.req.UserLoginDTO
     * @see com.timetracker.tracker.dto.resp.LoggedUserDTO
     */
    public LoggedUserDTO loginUser(UserLoginDTO req) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                req.getEmail(),
                req.getPassword()
        ));

        User user = (User) userDetailsService.loadUserByUsername(req.getEmail());

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new PasswordMismatchException();
        }

        return generatePairOfTokens(user);
    }


    /**
     * Regenerates JWT tokens using a refresh token.
     *
     * @param refreshToken The refresh token for re-logging in the user.
     * @return The LoggedUserDTO with new JWT tokens.
     * @see com.timetracker.tracker.dto.resp.LoggedUserDTO
     */
    @Override
    public LoggedUserDTO reLoginUser(@Valid @NotBlank(message = TOKEN_CANNOT_BE_NULL_OR_EMPTY) String refreshToken) {
        if (!jwtService.validateRefreshToken(refreshToken)) {
            throw new InvalidRefreshTokenException();
        }

        String email = jwtService.getRefreshClaims(refreshToken).getSubject();
        User user = (User) userDetailsService.loadUserByUsername(email);

        return generatePairOfTokens(user);
    }

    /**
     * Generates a pair of access and refresh tokens for the specified user.
     *
     * @param user the user for whom the tokens are generated
     * @return a LoggedUserDTO containing the generated access token, refresh token, and user details
     * @see com.timetracker.tracker.entities.User
     * @see com.timetracker.tracker.services.RefreshTokenService
     */
    private LoggedUserDTO generatePairOfTokens(User user) {
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        refreshTokenService.saveRefreshToken(refreshToken, user);
        return LoggedUserDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userDTO(UserMapper.INSTANCE.toDTO(user))
                .build();
    }
}
