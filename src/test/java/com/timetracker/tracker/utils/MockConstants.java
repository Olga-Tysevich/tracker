package com.timetracker.tracker.utils;

import java.util.Random;

public class MockConstants {
    public static final String BASE_URL = "http://localhost:8080/api/tracker";
    public static final String LOGIN_ENDPOINT = "/auth/login";
    public static final String VALID_EMAIL = "user%s@test.by";
    public static final String VALID_PASSWORD = "$2a$10$D5Yt0GsZNmvY3tz2DP1VPOA2aXWDXYGIfobB6mLP1BpeSHoXtf4pe";
    public static final String VALID_PASSWORD_DECODED = "973341";
    public static final String INVALID_PASSWORD_DECODED = "973341abc";
    public static final String SCHEME_SOURCE_PATH = "test_schemes";
    public static final String LOGGED_USER_SCHEME = "/logged_user.json";
    public static final int NUMBER_OF_OBJECTS = 10;
    public static final int DEFAULT_APP_PORT = 8080;
    public static final long DEFAULT_TIMEOUT = 1500L;
    public static final Random RANDOM = new Random();

    private MockConstants() {

    }
}
