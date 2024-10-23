package com.timetracker.tracker.controllers;

import com.timetracker.tracker.entities.User;
import com.timetracker.tracker.utils.MockConstants;
import com.timetracker.tracker.utils.MockUtils;
import com.timetracker.tracker.utils.converters.UserMapperForTests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;

import java.util.stream.Stream;

import static com.timetracker.tracker.utils.Constants.*;
import static com.timetracker.tracker.utils.MockConstants.*;
import static io.restassured.RestAssured.given;

class AuthControllerTest extends BaseTest {
    private final UserMapperForTests userMapper = UserMapperForTests.INSTANCE;

    @ParameterizedTest
    @MethodSource("cases")
    void whenUserAuth_thenStatus200AndReturnLoggedUserDTOTest(User user) {
        userRepository.save(user);
        checkStatusCodeAndBodyInPostRequest(
                LOGIN_ENDPOINT,
                HttpStatus.OK.value(),
                MockConstants.SCHEME_SOURCE_PATH + MockConstants.LOGGED_USER_SCHEME,
                userMapper.toValidUserLoginDTO(user),
                null
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void whenUserNotFound_thenStatus404Test(User user) {
        checkStatusCodeInPostRequest(
                LOGIN_ENDPOINT,
                HttpStatus.NOT_FOUND.value(),
                userMapper.toInvalidUserLoginDTO(user),
                null
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void whenUserCredInvalid_thenStatus403Test(User user) {
        userRepository.save(user);
        checkStatusCodeInPostRequest(
                LOGIN_ENDPOINT,
                HttpStatus.FORBIDDEN.value(),
                userMapper.toInvalidUserLoginDTO(user),
                null
        );
    }


    @Test
    void whenLogout_thenSetCookieTest() {
        given(requestSpecification)
                .header(TOKEN_HEADER, TOKEN_TYPE + getRefreshToken())
                .when()
                .post(LOGOUT_ENDPOINT)
                .then()
                .statusCode(HttpStatus.OK.value());

    }

    @Test
    void whenRefresh_thenSetCookieTest() {
        String token = getRefreshToken();
        given(requestSpecification)
                .cookie(REFRESH_TOKEN_KEY, token)
                .when()
                .post(REFRESH_TOKEN_ENDPOINT)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void whenInvalidRefreshToken_thenStatus401() {
        given(requestSpecification)
                .cookie(REFRESH_TOKEN_KEY, INVALID_TOKEN)
                .when()
                .post(REFRESH_TOKEN_ENDPOINT)
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    private String getRefreshToken() {
        return given(requestSpecification)
                .body(MockConstants.ADMIN_CRED)
                .when()
                .post(MockConstants.LOGIN_ENDPOINT)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .cookie(REFRESH_TOKEN_KEY);
    }

    static Stream<Arguments> cases() {
        return MockUtils.generateUsers(MockConstants.NUMBER_OF_OBJECTS).stream()
                .map(Arguments::of);
    }
}