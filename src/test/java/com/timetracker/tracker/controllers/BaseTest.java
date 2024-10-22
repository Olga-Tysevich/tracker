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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

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
        requestSpecification = RestAssured.given()
                .baseUri(MockConstants.BASE_URL)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON);
    }

    @BeforeEach
    public void insertData() {
        Set<User> users = new HashSet<>();
        Set<Project> projects = new HashSet<>();
        List<Record> records = MockUtils.generateRecord(MockConstants.NUMBER_OF_OBJECTS);

        IntStream.range(0, records.size())
                .forEach(i -> {
                    Record record = records.get(i);
                    users.add(record.getUser());
                    projects.add(record.getProject());
                });

        userRepository.saveAll(users);
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

    protected ValidatableResponse checkStatusCodeInGetResponse(String url, int code, String schema) {
        return RestAssured.given(requestSpecification)
                .get(url)
                .then()
                .statusCode(code)
                .body(matchesJsonSchemaInClasspath(schema))
                .time(lessThan(MockConstants.DEFAULT_TIMEOUT));
    }

    protected ValidatableResponse checkSuccessStatusCodeInPostResponse(String url, int code, String schema, Object requestBody) {
        return RestAssured.given(requestSpecification)
                .body(requestBody)
                .port(MockConstants.DEFAULT_APP_PORT)
                .post(url)
                .then()
                .statusCode(code)
                .body(matchesJsonSchemaInClasspath(schema))
                .time(lessThan(MockConstants.DEFAULT_TIMEOUT));
    }

    protected ValidatableResponse checkFailedStatusCodeInPostResponse(String url, int code, Object requestBody) {
        return RestAssured.given(requestSpecification)
                .body(requestBody)
                .port(MockConstants.DEFAULT_APP_PORT)
                .post(url)
                .then()
                .statusCode(code)
                .time(lessThan(MockConstants.DEFAULT_TIMEOUT));
    }

}
