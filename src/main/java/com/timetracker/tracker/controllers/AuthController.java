package com.timetracker.tracker.controllers;

import com.timetracker.tracker.dto.req.UserLoginDTO;
import com.timetracker.tracker.dto.resp.LoggedUserDTO;
import com.timetracker.tracker.services.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.timetracker.tracker.utils.Constants.REFRESH_TOKEN_KEY;

/**
 * Controller class for handling authentication related endpoints
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/tracker/auth")
public class AuthController {
    /**
     * AuthService interface
     *
     * @see com.timetracker.tracker.services.AuthService
     */
    private final AuthService authService;

    /**
     * Endpoint for user login.
     *
     * @param req The UserLoginDTO object containing login credentials.
     * @return ResponseEntity with logged-in user details and JWT refresh token cookie.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginDTO req) {
        LoggedUserDTO respBody = authService.loginUser(req);
        ResponseCookie cookie = getCookie(respBody.getRefreshToken());
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(respBody);
    }

    /**
     * Endpoint for refreshing a user's access token using the refresh token.
     *
     * @param token The JWT refresh token extracted from the cookie.
     * @return ResponseEntity with updated logged-in user details and new refresh token cookie.
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@CookieValue(REFRESH_TOKEN_KEY) String token) {
        LoggedUserDTO respBody = authService.reLoginUser(token);
        ResponseCookie cookie = getCookie(respBody.getRefreshToken());
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(respBody);
    }

    /**
     * Endpoint for logging a user out, removing the JWT refresh token cookie.
     *
     * @return ResponseEntity with removed JWT refresh token cookie.
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN_KEY)
                .value(null)
                .maxAge(0)
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }

    /**
     * Creating the JWT refresh token cookie.
     *
     * @param refreshToken The JWT refresh token value.
     * @return ResponseCookie containing the JWT refresh token.
     */
    private ResponseCookie getCookie(@NotBlank String refreshToken) {
        return ResponseCookie.from(REFRESH_TOKEN_KEY, refreshToken)
                .httpOnly(true)
                .path("/api/tracker/auth/refresh")
                .build();
    }
}
