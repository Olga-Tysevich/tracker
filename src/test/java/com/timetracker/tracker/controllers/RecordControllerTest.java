package com.timetracker.tracker.controllers;

import com.timetracker.tracker.dto.req.CreateRecordDTO;
import com.timetracker.tracker.dto.req.UpdateRecordDTO;
import com.timetracker.tracker.dto.resp.RecordDTO;
import com.timetracker.tracker.entities.Project;
import com.timetracker.tracker.entities.Record;
import com.timetracker.tracker.entities.User;
import com.timetracker.tracker.utils.MockConstants;
import com.timetracker.tracker.utils.MockUtils;
import com.timetracker.tracker.utils.converters.RecordMapperForTests;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.timetracker.tracker.utils.MockConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RecordControllerTest extends BaseTest {

    @Test
    void whenGetRecords_thanStatus200AndGetRecordsForPageDTOTest() {
        List<User> users = MockUtils.generateUsers(DEFAULT_NUMBER_OF_OBJECTS);
        List<Project> projects = MockUtils.generateProjects(DEFAULT_NUMBER_OF_OBJECTS);
        userRepository.saveAll(users);
        projectRepository.saveAll(projects);
        List<Record> allRecords = MockUtils.generateRecords(users, projects, DEFAULT_NUMBER_OF_OBJECTS);
        recordRepository.saveAll(allRecords);
        User user = users.get(RANDOM.nextInt(users.size()));
        long recordsForUser = allRecords.stream().filter(r -> r.getUser() == user).count();

        long itemsPerPage = DEFAULT_COUNT_PER_PAGE > allRecords.size() ? allRecords.size() : DEFAULT_COUNT_PER_PAGE;
        ValidatableResponse responseForAdmin = checkStatusCodeAndBodyInGetObjectForPageReq(
                GET_RECORDS_FOR_ADMIN_ENDPOINT,
                HttpStatus.OK.value(),
                SCHEME_SOURCE_PATH + RECORDS_FOR_PAGE_SCHEME,
                ADMIN_CRED
        );

        ValidatableResponse responseForUser = checkStatusCodeAndBodyInGetObjectForPageReq(
                GET_RECORDS_FOR_USER_ENDPOINT,
                HttpStatus.OK.value(),
                SCHEME_SOURCE_PATH + RECORDS_FOR_PAGE_SCHEME,
                USER_CRED.replaceFirst(USER_EMAIL, user.getEmail())
        );

        List<RecordDTO> recordsForPage = responseForAdmin.extract().jsonPath().getList("recordsForPage", RecordDTO.class);
        assertEquals(itemsPerPage, recordsForPage.size(), "The size of the projects list should be " + itemsPerPage);

        int totalItemsForAdmin = responseForAdmin.extract().jsonPath().getInt(TOTAL_ITEMS_KEY);
        int totalItemsForUser = responseForUser.extract().jsonPath().getInt(TOTAL_ITEMS_KEY);
        assertEquals(allRecords.size(), totalItemsForAdmin, "The totalItems for admin should be " + allRecords.size());
        assertEquals(recordsForUser, totalItemsForUser, "The totalItems for user should be " + recordsForUser);
    }

    @Test
    void whenInvalidReqGetRecordsForAdmin_thenStatus400Test() {
        checkStatusCodeInGetRequest(
                GET_RECORDS_FOR_ADMIN_ENDPOINT,
                HttpStatus.BAD_REQUEST.value(),
                MockConstants.ADMIN_CRED
        );
    }

    @Test
    void whenInvalidReqGetRecordsForUser_thenStatus400Test() {
        checkStatusCodeInGetRequest(
                GET_RECORDS_FOR_USER_ENDPOINT,
                HttpStatus.BAD_REQUEST.value(),
                USER_CRED
        );
    }

    @Test
    void whenCreateRecord_thanStatus201Test() {
        CreateRecordDTO recordDTO = RecordMapperForTests.INSTANCE.toCreateRecordDTO(getRecord());
        checkStatusCodeInPostRequest(
                CREATE_RECORD_ENDPOINT,
                HttpStatus.CREATED.value(),
                recordDTO,
                USER_CRED
        );
    }

    @Test
    void whenInvalidCreateRecord_thanStatus400Test() {
        CreateRecordDTO recordDTO = RecordMapperForTests.INSTANCE.toCreateRecordDTO(getRecord());
        recordDTO.setUserId(null);
        checkStatusCodeInPostRequest(
                CREATE_RECORD_ENDPOINT,
                HttpStatus.BAD_REQUEST.value(),
                recordDTO,
                USER_CRED
        );
    }

    @Test
    void whenUpdateRecord_thanStatus200Test() {
        Record record = getRecord();
        recordRepository.save(record);
        UpdateRecordDTO recordDTO = RecordMapperForTests.INSTANCE.toUpdateRecordDTO(record);
        checkStatusCodeInPostRequest(
                UPDATE_RECORD_ENDPOINT,
                HttpStatus.OK.value(),
                recordDTO,
                USER_CRED
        );
    }

    @Test
    void whenInvalidRequestUpdateRecord_thanStatus400Test() {
        checkStatusCodeInPostRequest(
                UPDATE_RECORD_ENDPOINT,
                HttpStatus.BAD_REQUEST.value(),
                new UpdateRecordDTO(),
                USER_CRED
        );
    }

    @Test
    void whenDeleteRecord_thanStatus200Test() {
        Record forDelete = getRecord();
        recordRepository.save(forDelete);
        checkStatusCodeInDeleteRequest(
                DELETE_RECORD_ENDPOINT,
                HttpStatus.OK.value(),
                forDelete.getId(),
                USER_CRED
        );
    }

    @Test
    void whenBadRequestRecord_thanStatus400Test() {
        checkStatusCodeInDeleteRequest(
                DELETE_RECORD_ENDPOINT,
                HttpStatus.BAD_REQUEST.value(),
                null,
                USER_CRED
        );
    }

    private Record getRecord() {
        User user = userRepository.getByEmail(USER_EMAIL)
                .orElseGet(() -> userRepository.save(MockUtils.getUser()));
        Project project = MockUtils.generateProjects(1).get(0);
        projectRepository.save(project);
        return MockUtils.generateRecords(List.of(user), List.of(project), 1).get(0);
    }
}