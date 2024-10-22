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

import static com.timetracker.tracker.utils.MockConstants.LOGIN_ENDPOINT;

class AuthControllerTest extends BaseTest {
    private final UserMapperForTests userMapper = UserMapperForTests.INSTANCE;

    @ParameterizedTest
    @MethodSource("cases")
    void whenUserAuth_thenStatus200AndGetLoggedUserDTOTest(User user) {
        userRepository.save(user);
        checkSuccessStatusCodeInPostResponse(
                LOGIN_ENDPOINT,
                HttpStatus.OK.value(),
                MockConstants.SCHEME_SOURCE_PATH + MockConstants.LOGGED_USER_SCHEME,
                userMapper.toValidUserLoginDTO(user)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void whenUserNotFound_thenStatus404(User user) {
        checkFailedStatusCodeInPostResponse(
                LOGIN_ENDPOINT,
                HttpStatus.NOT_FOUND.value(),
                userMapper.toInvalidUserLoginDTO(user)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void whenUserCredInvalid_thenStatus403(User user) {
        userRepository.save(user);
        checkFailedStatusCodeInPostResponse(
                LOGIN_ENDPOINT,
                HttpStatus.FORBIDDEN.value(),
                userMapper.toInvalidUserLoginDTO(user)
        );
    }

    @Test
    void login() {
    }

    @Test
    void refresh() {
    }

    @Test
    void logout() {
    }

    static Stream<Arguments> cases() {
        return MockUtils.generateUser(MockConstants.NUMBER_OF_OBJECTS).stream()
                .map(Arguments::of);
    }
}