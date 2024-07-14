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

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

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

    @Override
    public LoggedUserDTO regeneratePairOfTokens(@Valid @NotBlank(message = "Token cannot be null or empty!")
                                                    String refreshToken) {
        if (!jwtProvider.validateRefreshToken(refreshToken)) {
            throw new InvalidRefreshTokenException();
        }
        String email = jwtProvider.getRefreshClaims(refreshToken).getSubject();
        User user = userRepository.getByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        return generatePairOfTokens(user);
    }

    private void saveRefreshToken(@Valid @Email String email,
                                  @Valid @NotBlank(message = "Token cannot be null or empty!") String token) {
        if (!userRepository.existsByEmail(email)) {
            throw new UserNotFoundException();
        }
        refreshTokenRepository.save(new RefreshToken(email, token));
    }

}
