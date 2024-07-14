package com.timetracker.tracker.services.impl;

import com.timetracker.tracker.conf.JwtProvider;
import com.timetracker.tracker.dto.req.UserLoginDTO;
import com.timetracker.tracker.dto.resp.LoggedUserDTO;
import com.timetracker.tracker.entities.User;
import com.timetracker.tracker.exceptions.WrongPasswordException;
import com.timetracker.tracker.services.AuthService;
import com.timetracker.tracker.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public LoggedUserDTO loginUser(UserLoginDTO req) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                req.getEmail(),
                req.getPassword()
        ));

        User user = (User) userDetailsService.loadUserByUsername(req.getEmail());

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new WrongPasswordException();
        }

        return jwtService.generatePairOfTokens(user);
    }

    @Override
    public LoggedUserDTO reLoginUser(String refreshToken) {
        return jwtService.regeneratePairOfTokens(refreshToken);
    }
}
