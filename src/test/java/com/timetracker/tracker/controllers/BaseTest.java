package com.timetracker.tracker.controllers;

import com.timetracker.tracker.entities.Project;
import com.timetracker.tracker.entities.Record;
import com.timetracker.tracker.entities.User;
import com.timetracker.tracker.repositories.ProjectRepository;
import com.timetracker.tracker.repositories.RecordRepository;
import com.timetracker.tracker.repositories.RefreshTokenRepository;
import com.timetracker.tracker.repositories.UserRepository;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static com.timetracker.tracker.utils.Constants.TOKEN_HEADER;
import static com.timetracker.tracker.utils.Constants.TOKEN_TYPE;
import static com.timetracker.tracker.utils.MockConstants.USER_ID_PARAM;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

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
        requestSpecification = RestAssured.given()
                .baseUri(MockConstants.BASE_URL)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON);
    }

    @BeforeEach
    public void insertData() {
        Set<User> users = new HashSet<>();
        Set<Project> projects = new HashSet<>();
        List<Record> records = MockUtils.generateRecords(MockConstants.NUMBER_OF_OBJECTS);

        IntStream.range(0, records.size())
                .forEach(i -> {
                    Record record = records.get(i);
                    users.add(record.getUser());
                    projects.add(record.getProject());
                });

        userRepository.saveAll(users);
        userRepository.save(MockUtils.getAdmin());
        userRepository.save(MockUtils.getUser());
        projectRepository.saveAll(projects);
        recordRepository.saveAll(records);
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
