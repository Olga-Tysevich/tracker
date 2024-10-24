package com.timetracker.tracker.controllers;

import com.timetracker.tracker.dto.req.CreateUserDTO;
import com.timetracker.tracker.dto.resp.UserDTO;
import com.timetracker.tracker.entities.User;
import com.timetracker.tracker.entities.enums.RoleEnum;
import com.timetracker.tracker.utils.MockConstants;
import com.timetracker.tracker.utils.MockUtils;
import com.timetracker.tracker.utils.converters.UserMapperForTests;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.timetracker.tracker.utils.MockConstants.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest extends BaseTest {

    @Test
    void whenGetUsers_thenStatus200AndReturnUsersForPageDTOTest() {
        List<User> users = MockUtils.generateUsers(DEFAULT_NUMBER_OF_OBJECTS);
        userRepository.saveAll(users);
        List<User> allUsers = userRepository.findAll();
        long itemsPerPage = DEFAULT_COUNT_PER_PAGE > allUsers.size() ? allUsers.size() : DEFAULT_COUNT_PER_PAGE;
        ValidatableResponse response = checkStatusCodeAndBodyInGetObjectForPageReq(
                GET_USERS_FOR_ADMIN_ENDPOINT,
                HttpStatus.OK.value(),
                SCHEME_SOURCE_PATH + USERS_FOR_PAGE_SCHEME,
                ADMIN_CRED
        );

        List<UserDTO> usersForPage = response.extract().jsonPath().getList("usersForPage", UserDTO.class);
        assertEquals(itemsPerPage, usersForPage.size(), "The size of the projects list should be " + itemsPerPage);

        int totalItems = response.extract().jsonPath().getInt(TOTAL_ITEMS_KEY);
        assertEquals(allUsers.size(), totalItems, "The totalItems should be " + allUsers.size());
    }

    @Test
    void whenInvalidReqGetUsers_thenStatus400Test() {
        checkStatusCodeInGetRequest(
                GET_USERS_FOR_ADMIN_ENDPOINT,
                HttpStatus.BAD_REQUEST.value(),
                MockConstants.ADMIN_CRED
        );
    }

    @Test
    void whenRoleIsNotAdminReqGetUsers_thenStatus403Test() {
        checkStatusCodeInGetRequest(
                GET_USERS_FOR_ADMIN_ENDPOINT,
                HttpStatus.FORBIDDEN.value(),
                MockConstants.USER_CRED
        );
    }

    @Test
    void whenCreateUser_thanStatus201Test() {
        CreateUserDTO user = MockUtils.getCreateUserDTO();
        checkStatusCodeInPostRequest(
                CREATE_USER_FOR_ADMIN_ENDPOINT,
                HttpStatus.CREATED.value(),
                user,
                MockConstants.ADMIN_CRED
        );
    }

    @Test
    void whenInvalidCreateUser_thanStatus400Test() {
        CreateUserDTO user = MockUtils.getCreateUserDTO();
        user.setEmail(null);
        checkStatusCodeInPostRequest(
                CREATE_USER_FOR_ADMIN_ENDPOINT,
                HttpStatus.BAD_REQUEST.value(),
                user,
                MockConstants.ADMIN_CRED
        );
    }

    @Test
    void whenRoleIsNotAdminCreateUser_thanStatus403Test() {
        CreateUserDTO user = MockUtils.getCreateUserDTO();
        checkStatusCodeInPostRequest(
                CREATE_USER_FOR_ADMIN_ENDPOINT,
                HttpStatus.FORBIDDEN.value(),
                user,
                USER_CRED
        );
    }

    @Test
    void whenGetCurrentUser_thanStatus200AndReturnUserDTOTest() {
        ValidatableResponse userResp = checkStatusCodeAndBodyInGetRequest(
                GET_CURRENT_USER_ENDPOINT,
                HttpStatus.OK.value(),
                SCHEME_SOURCE_PATH + USER_SCHEME,
                USER_CRED
        );

        ValidatableResponse adminResp = checkStatusCodeAndBodyInGetRequest(
                GET_CURRENT_USER_ENDPOINT,
                HttpStatus.OK.value(),
                SCHEME_SOURCE_PATH + USER_SCHEME,
                ADMIN_CRED
        );

        userResp.assertThat()
                .body(USER_NAME_KEY, equalTo(VALID_USER_NAME))
                .body(USER_SURNAME_KEY, equalTo(VALID_USER_SURNAME))
                .body(USER_EMAIL_KEY, equalTo(USER_EMAIL))
                .body(USER_ROLES_KEY, containsInAnyOrder(RoleEnum.ROLE_USER.name()));

        adminResp.assertThat()
                .body(USER_NAME_KEY, equalTo(ADMIN_EMAIL))
                .body(USER_SURNAME_KEY, equalTo(ADMIN_EMAIL))
                .body(USER_EMAIL_KEY, equalTo(ADMIN_EMAIL))
                .body(USER_ROLES_KEY, containsInAnyOrder(RoleEnum.ROLE_ADMIN.name()));
    }
    @Test
    void whenUpdateUser_thanStatus200Test() {
        User forUpdate = userRepository.findAll().get(0);
        forUpdate.setPassword(VALID_PASSWORD + 123);
        checkStatusCodeInPostRequest(
                UPDATE_USER_ENDPOINT,
                HttpStatus.OK.value(),
                UserMapperForTests.INSTANCE.toUpdateUserDTO(forUpdate),
                USER_CRED
        );
    }

    @Test
    void whenInvalidRequestUpdateUser_thanStatus400Test() {
        User forUpdate = MockUtils.getUser();
        checkStatusCodeInPostRequest(
                UPDATE_USER_ENDPOINT,
                HttpStatus.BAD_REQUEST.value(),
                UserMapperForTests.INSTANCE.toUpdateUserDTO(forUpdate),
                USER_CRED
        );
    }

    @Test
    void whenDeleteUser_thanStatus200Test() {
        User forDelete = MockUtils.generateUsers(1).get(0);
        userRepository.save(forDelete);
        checkStatusCodeInDeleteRequest(
                DELETE_USER_ENDPOINT,
                HttpStatus.OK.value(),
                forDelete.getId(),
                ADMIN_CRED
        );
    }

    @Test
    void whenBadRequestDeleteUser_thanStatus400Test() {
        checkStatusCodeInDeleteRequest(
                DELETE_USER_ENDPOINT,
                HttpStatus.BAD_REQUEST.value(),
                null,
                ADMIN_CRED
        );
    }

    @Test
    void whenRoleIsNotAdminDeleteUser_thanStatus403Test() {
        User forDelete = userRepository.findAll().get(0);
        checkStatusCodeInDeleteRequest(
                DELETE_USER_ENDPOINT,
                HttpStatus.FORBIDDEN.value(),
                forDelete.getId(),
                USER_CRED
        );
    }

}