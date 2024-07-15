package com.timetracker.tracker.services.impl;

import com.timetracker.tracker.dto.req.UserLoginDTO;
import com.timetracker.tracker.dto.resp.LoggedUserDTO;
import com.timetracker.tracker.entities.User;
import com.timetracker.tracker.exceptions.PasswordMismatchException;
import com.timetracker.tracker.services.AuthService;
import com.timetracker.tracker.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of AuthService interface.
 *
 * @see com.timetracker.tracker.services.AuthService
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class AuthServiceImpl implements AuthService {
    /**
     * UserDetailsService bean.
     *
     * @see com.timetracker.tracker.services.impl.UserDetailsServiceImpl
     */
    private final UserDetailsService userDetailsService;
    /**
     * AuthenticationManager bean.
     *
     * @see com.timetracker.tracker.conf.WebSecurityConfig
     */
    private final AuthenticationManager authenticationManager;
    /**
     * PasswordEncoder bean.
     *
     * @see com.timetracker.tracker.conf.WebSecurityConfig
     */
    private final PasswordEncoder passwordEncoder;
    /**
     * JwtService bean.
     *
     * @see com.timetracker.tracker.services.JwtService
     */
    private final JwtService jwtService;

    /**
     * Authenticates user with provided credentials and generates JWT tokens.
     *
     * @param req The UserLoginDTO containing user login credentials.
     * @return The LoggedUserDTO with JWT tokens.
     * @see com.timetracker.tracker.dto.resp.LoggedUserDTO
     * @see com.timetracker.tracker.services.JwtService
     */
    @Override
    public LoggedUserDTO loginUser(UserLoginDTO req) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                req.getEmail(),
                req.getPassword()
        ));

        User user = (User) userDetailsService.loadUserByUsername(req.getEmail());

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new PasswordMismatchException();
        }

        return jwtService.generatePairOfTokens(user);
    }

    /**
     * Regenerates JWT tokens using a refresh token.
     *
     * @param refreshToken The refresh token for re-logging in the user.
     * @return The LoggedUserDTO with new JWT tokens.
     * @see com.timetracker.tracker.dto.resp.LoggedUserDTO
     * @see com.timetracker.tracker.services.JwtService
     */
    @Override
    public LoggedUserDTO reLoginUser(String refreshToken) {
        return jwtService.regeneratePairOfTokens(refreshToken);
    }
}
