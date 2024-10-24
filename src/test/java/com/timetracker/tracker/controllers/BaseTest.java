package com.timetracker.tracker.controllers;

import com.timetracker.tracker.repositories.ProjectRepository;
import com.timetracker.tracker.repositories.RecordRepository;
import com.timetracker.tracker.repositories.RefreshTokenRepository;
import com.timetracker.tracker.repositories.UserRepository;
import com.timetracker.tracker.utils.DotenvLoader;
import com.timetracker.tracker.utils.MockConstants;
import com.timetracker.tracker.utils.MockUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;

import static com.timetracker.tracker.utils.Constants.TOKEN_HEADER;
import static com.timetracker.tracker.utils.Constants.TOKEN_TYPE;
import static com.timetracker.tracker.utils.MockConstants.*;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.lessThan;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BaseTest {
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ProjectRepository projectRepository;

    @Autowired
    protected RecordRepository recordRepository;

    @Autowired
    protected RefreshTokenRepository tokenRepository;

    protected static RequestSpecification requestSpecification;


    @BeforeAll
    static void setUp() {
        DotenvLoader.load();

        requestSpecification = RestAssured.given()
                .baseUri(MockConstants.BASE_URL)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON);
    }

    @BeforeEach
    public void insertData() {
        userRepository.save(MockUtils.getAdmin());
        userRepository.save(MockUtils.getUser());
    }

    @AfterEach
    public void deleteData() {
        recordRepository.deleteAll();
        projectRepository.deleteAll();
        tokenRepository.deleteAll();
        userRepository.deleteAll();
    }

    protected ValidatableResponse checkStatusCodeInGetRequest(String url, int code, String validUserDataJson) {
        String accessToken = getAccessToken(validUserDataJson);
        return RestAssured.given(requestSpecification)
                .header(TOKEN_HEADER, TOKEN_TYPE + accessToken)
                .when()
                .get(url)
                .then()
                .statusCode(code)
                .time(lessThan(MockConstants.DEFAULT_TIMEOUT));
    }

    protected ValidatableResponse checkStatusCodeInDeleteRequest(String url, int code, Long id, String validUserDataJson) {
        String accessToken = getAccessToken(validUserDataJson);
        return RestAssured.given(requestSpecification)
                .header(TOKEN_HEADER, TOKEN_TYPE + accessToken)
                .param(USER_ID_PARAM, id)
                .when()
                .delete(url)
                .then()
                .statusCode(code)
                .time(lessThan(MockConstants.DEFAULT_TIMEOUT));
    }

    protected ValidatableResponse checkStatusCodeAndBodyInGetRequest(String url, int code, String schema,
                                                                     String validUserDataJson) {
        String accessToken = getAccessToken(validUserDataJson);
        return RestAssured.given(requestSpecification)
                .header(TOKEN_HEADER, TOKEN_TYPE + accessToken)
                .when()
                .get(url)
                .then()
                .statusCode(code)
                .body(matchesJsonSchemaInClasspath(schema))
                .time(lessThan(MockConstants.DEFAULT_TIMEOUT));
    }

    protected ValidatableResponse checkStatusCodeAndBodyInGetObjectForPageReq(String url, int code, String schema,
                                                                              String validUserDataJson) {
        String accessToken = getAccessToken(validUserDataJson);
        return given(requestSpecification)
                .header(TOKEN_HEADER, TOKEN_TYPE + accessToken)
                .param(COUNT_PER_PAGE_PARAM, DEFAULT_COUNT_PER_PAGE)
                .param(PAGE_PARAM, DEFAULT_PAGE)
                .when()
                .get(url)
                .then()
                .statusCode(code)
                .body(matchesJsonSchemaInClasspath(schema))
                .time(lessThan(MockConstants.DEFAULT_TIMEOUT));
    }

    protected ValidatableResponse checkStatusCodeAndBodyInPostRequest(String url, int code, String schema,
                                                                      Object requestBody, String validUserDataJson) {
        String accessToken = getAccessToken(validUserDataJson);
        return RestAssured.given(requestSpecification)
                .header(TOKEN_HEADER, TOKEN_TYPE + accessToken)
                .body(requestBody)
                .port(MockConstants.DEFAULT_APP_PORT)
                .post(url)
                .then()
                .statusCode(code)
                .body(matchesJsonSchemaInClasspath(schema))
                .time(lessThan(MockConstants.DEFAULT_TIMEOUT));
    }

    protected ValidatableResponse checkStatusCodeInPostRequest(String url, int code,
                                                               Object requestBody, String validUserDataJson) {
        String accessToken = getAccessToken(validUserDataJson);
        Object o = RestAssured.given(requestSpecification)
                .header(TOKEN_HEADER, TOKEN_TYPE + accessToken)
                .body(requestBody);
        return RestAssured.given(requestSpecification)
                .header(TOKEN_HEADER, TOKEN_TYPE + accessToken)
                .body(requestBody)
                .port(MockConstants.DEFAULT_APP_PORT)
                .post(url)
                .then()
                .statusCode(code)
                .time(lessThan(MockConstants.DEFAULT_TIMEOUT));
    }

    protected String getAccessToken(String validUserDataJson) {
        return given(requestSpecification)
                .body(validUserDataJson)
                .when()
                .post(MockConstants.LOGIN_ENDPOINT)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().response()
                .jsonPath()
                .getString("accessToken");
    }

}
