package com.timetracker.tracker.utils;

import java.util.Random;

public class MockConstants {
    public static final String BASE_URL = "http://localhost:8080/api/tracker";
    public static final String LOGIN_ENDPOINT = "/auth/login";
    public static final String LOGOUT_ENDPOINT = "/auth/logout";
    public static final String REFRESH_TOKEN_ENDPOINT = "/auth/refresh";
    public static final String GET_USERS_FOR_ADMIN_ENDPOINT = "/user/admin/get";
    public static final String CREATE_USER_FOR_ADMIN_ENDPOINT = "/user/admin/create";
    public static final String GET_CURRENT_USER_ENDPOINT = "/user";
    public static final String UPDATE_USER_ENDPOINT = "/user/update";
    public static final String DELETE_USER_ENDPOINT = "/user/admin/delete";
    public static final String ADMIN_EMAIL = "admin@test.by";
    public static final String USER_NAME_KEY = "name";
    public static final String USER_SURNAME_KEY = "surname";
    public static final String USER_EMAIL_KEY = "email";
    public static final String USER_ROLES_KEY = "roles";
    public static final String VALID_USER_NAME = "username";
    public static final String VALID_USER_SURNAME = "usersurname";
    public static final String VALID_USER_EMAIL = "usertest@test.com";
    public static final String USER_EMAIL = "user@test.com";
    public static final String VALID_EMAIL = "user%s@test.by";
    public static final String VALID_PASSWORD = "$2a$10$D5Yt0GsZNmvY3tz2DP1VPOA2aXWDXYGIfobB6mLP1BpeSHoXtf4pe";
    public static final String VALID_PASSWORD_DECODED = "973341";
    public static final String INVALID_PASSWORD_DECODED = "973341abc";
    public static final String SCHEME_SOURCE_PATH = "test_schemes";
    public static final String USER_SCHEME = "/user.json";
    public static final String LOGGED_USER_SCHEME = "/logged_user.json";
    public static final String USERS_FOR_PAGE_SCHEME = "/users_for_page.json";
    public static final String ADMIN_CRED = "{\"email\": \"admin@test.by\", \"password\": \"973341\"}";
    public static final String USER_CRED = "{\"email\": \"" + USER_EMAIL + "\", \"password\": \"973341\"}";
    public static final String INVALID_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    public static final int NUMBER_OF_OBJECTS = 10;
    public static final int DEFAULT_APP_PORT = 8080;
    public static final long DEFAULT_TIMEOUT = 1500L;
    public static final String PAGE_PARAM = "pageNum";
    public static final String USER_ID_PARAM = "id";
    public static final long DEFAULT_PAGE = 1;
    public static final String COUNT_PER_PAGE_PARAM = "countPerPage";
    public static final long DEFAULT_COUNT_PER_PAGE = 5;
    public static final Random RANDOM = new Random();

    private MockConstants() {

    }
}
