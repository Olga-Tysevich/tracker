package com.timetracker.tracker.facades.impl;

import com.timetracker.tracker.conf.JwtProvider;
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
     * @see JwtProvider
     */
    private final JwtProvider jwtProvider;

    /**
     * Authenticates user with provided credentials and generates JWT tokens.
     *
     * @param req The UserLoginDTO containing user login credentials.
     * @return The LoggedUserDTO with JWT tokens.
     * @see UserLoginDTO
     * @see LoggedUserDTO
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

        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);
        refreshTokenService.saveRefreshToken(refreshToken, user);
        return LoggedUserDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userDTO(UserMapper.INSTANCE.toDTO(user))
                .build();
    }


    /**
     * Regenerates JWT tokens using a refresh token.
     *
     * @param refreshToken The refresh token for re-logging in the user.
     * @return The LoggedUserDTO with new JWT tokens.
     * @see com.timetracker.tracker.dto.resp.LoggedUserDTO
     * @see RefreshTokenService
     */
    @Override
    public LoggedUserDTO reLoginUser(@Valid @NotBlank(message = TOKEN_CANNOT_BE_NULL_OR_EMPTY) String refreshToken) {
        String email = jwtProvider.getRefreshClaims(refreshToken).getSubject();
        User user = (User) userDetailsService.loadUserByUsername(email);

        if (!jwtProvider.validateRefreshToken(refreshToken)) {
            refreshTokenService.blockUserRefreshTokens(user);
            throw new InvalidRefreshTokenException();
        }

        return loginUser(new UserLoginDTO(user.getEmail(), user.getPassword()));
    }
}
