package com.timetracker.tracker.utils;

/**
 * The application constants class.
 */
public class Constants {
    public static final String USER_FULL_NAME_PATTERN = "%s %s";
    public static final String ID_CLAIM = "id";
    public static final String ROLE_CLAIM = "role";
    public static final String REFRESH_TOKEN_KEY = "refresh-token";
    public static final String TOKEN_TYPE = "Bearer ";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_WAS_STOLEN_MESSAGE = "Token was stolen!";
    public static final String TOKEN_CANNOT_BE_NULL_OR_EMPTY = "Token cannot be null or empty!";
    public static final String REQ_CANNOT_BE_NULL = "UpdateUserDTO cannot be null";
    public static final String FORBIDDEN_KEY = "Forbidden";
    public static final String FORBIDDEN_MESSAGE = "You do not have permission to access this resource";
    public static final String DESCRIPTION_CANNOT_BE_EMPTY = "Description cannot be null or empty!";
    public static final String NAME_CANNOT_BE_EMPTY = "Name cannot be null or empty!";
    public static final String SURNAME_CANNOT_BE_NULL_OR_EMPTY = "Surname cannot be null or empty!";
    public static final String EMAIL_CANNOT_BE_NULL_OR_EMPTY = "Email cannot be null or empty!";
    public static final String REFRESH_TOKEN_CANNOT_BE_NULL_OR_EMPTY = "Refresh token cannot be null or empty!";
    public static final String USER_ID_CANNOT_BE_NULL = "User ID cannot be null!";
    public static final String USER_CANNOT_BE_NULL = "User cannot be null!";
    public static final String MIN_USER_ID_MASSAGE = "The user ID must be greater than or equal to 1!";
    public static final String PROJECT_ID_CANNOT_BE_NULL = "Project ID cannot be null!";
    public static final String ROLE_ID_CANNOT_BE_NULL = "Role ID cannot be null!";
    public static final String ROLE_CANNOT_BE_NULL = "Role cannot be null!";
    public static final String PROJECT_CANNOT_BE_NULL = "Project cannot be null!";
    public static final String PROJECT_NAME_CANNOT_BE_NULL = "Project name cannot be null!";
    public static final String PROJECT_DESCRIPTION_CANNOT_BE_NULL = "Project description cannot be null!";
    public static final String PROJECT_ALREADY_EXIST = "Project already exist";
    public static final String RECORD_ID_CANNOT_BE_NULL = "Record ID cannot be null!";
    public static final String TOKEN_ID_CANNOT_BE_NULL = "Token ID cannot be null!";
    public static final String MIN_PROJECT_ID_MESSAGE = "The project ID must be greater than or equal to 1!";
    public static final String START_DATE_CANNOT_BE_NULL = "Start date cannot be null!";
    public static final String END_DATE_CANNOT_BE_NULL = "End date cannot be null!";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DURATION_PATTERN = "1w 1d 1h 1m";
    public static final String DURATION_CANNOT_BE_NULL = "Duration cannot be null!";
    public static final String USER_FULL_NAME_CANNOT_BE_NULL = "User full name cannot be null!";
    public static final String PASSWORD_CANNOT_BE_NULL_OR_EMPTY = "Password cannot be null or empty!";
    public static final String INVALID_PASSWORD_MESSAGE = "Be at least 6 characters long\n" +
            "Contain at least one digit (0-9)\n" +
            "Contain at least one special character (!@#$%^&*)\n" +
            "Contain at least one lowercase letter (a-z)\n" +
            "Contain at least one uppercase letter (A-Z)";
    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}$";
    public static final String PASSWORDS_DO_NOT_MATCH = "Passwords do not match!";
    public static final String REGEXP_EMAIL = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static final String INVALID_EMAIL_MESSAGE = "Invalid email format. Valid email example: example@domain.com";
    public static final String THE_ROLE_SET_CANNOT_BE_EMPTY = "The role set cannot be empty! You must select at least one user role!";
    public static final String PAGE_NUMBER_CANNOT_BE_NULL = "You must specify the page number!";
    public static final String PAGE_SIZE_CANNOT_BE_NULL = "You need to specify the size of posts on the page!";
    public static final String PROJECT_LIST_CANNOT_BE_NULL = "Project list cannot be null!";
    public static final String RECORD_LIST_CANNOT_BE_NULL = "Record list cannot be null!";
    public static final String TOTAL_ITEMS_CANNOT_BE_NULL = "Total items cannot be null!";
    public static final String PROJECT_NOT_FOUND = "Project not Found!";
    public static final String DURATION_FORMAT_PATTERN = "%dw%dd%dh%dm";
    public static final long MIN_ID = 1L;
    public static final int DAYS_IN_A_WEEK = 7;
    public static final int HOURS_IN_A_DAY = 24;
    public static final long MINUTES_IN_A_HOURS = 60L;
    public static final String[] IGNORE_URLS = {"/swagger-ui/**", "/swagger-resources/*", "/v3/api-docs/**"};
    public static final char[] DURATION_TOKENS = {'w', 'd', 'h', 'm'};
    public static final char WEEK_TOKEN = 'w';
    public static final char DAY_TOKEN = 'd';
    public static final char HOUR_TOKEN = 'h';
    public static final char MINUTES_TOKEN = 'm';
    public static final String INVALID_RECORD_PERIOD = "Invalid record period!";
    public static final String INVALID_REFRESH_TOKEN = "Invalid refresh token!";
    public static final String INVALID_USER_ROLE = "Invalid user role!";
    public static final String NOT_FOUND = "Not found!";
    public static final String OBJECT_ALREADY_EXIST = "Object already exist!";
    public static final String PASSWORDS_MISMATCH = "Passwords mismatch!";
    public static final String USER_IS_NOT_AUTHORIZED = "User is not authorized!";
    public static final String Unsupported_DTO_MESSAGE = "This method does not support this type of dto!";
    public static final String USER_ALREADY_EXIST = "User already exist!";
    public static final String USER_NOT_FOUND = "User not found!";
    public static final String TIMESTAMP_CANNOT_BE_NULL = "Timestamp cannot be null!";
    public static final String EXCEPTION_DETAILS_CANNOT_BE_EMPTY = "Exception details cannot be empty!";
    public static final String EXCEPTION_MESSAGE_CANNOT_BE_NULL = "Exception message cannot be null or empty!";
    public static final String HTTP_STATUS_CODE_CANNOT_BE_NULL = "Http status code cannot be null!";
    public static final String FIELD_NAME_CANNOT_BE_NULL = "Field name cannot be null!";
    public static final String MESSAGE_CANNOT_BE_NULL = "Message cannot be null!";

    private Constants() {
    }
}
