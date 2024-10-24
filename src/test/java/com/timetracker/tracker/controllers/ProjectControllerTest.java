package com.timetracker.tracker.controllers;

import com.timetracker.tracker.dto.req.CreateProjectDTO;
import com.timetracker.tracker.dto.req.UpdateProjectDTO;
import com.timetracker.tracker.dto.resp.ProjectDTO;
import com.timetracker.tracker.entities.Project;
import com.timetracker.tracker.utils.MockConstants;
import com.timetracker.tracker.utils.MockUtils;
import com.timetracker.tracker.utils.converters.ProjectMapperForTests;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.timetracker.tracker.utils.MockConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProjectControllerTest extends BaseTest {

    @Test
    void whenGetProjects_thanStatus200AndGetProjectsForPageDTOTest() {
        List<Project> allProjects = MockUtils.generateProjects(DEFAULT_NUMBER_OF_OBJECTS);
        projectRepository.saveAll(allProjects);
        long itemsPerPage = DEFAULT_COUNT_PER_PAGE > allProjects.size() ? allProjects.size() : DEFAULT_COUNT_PER_PAGE;
        ValidatableResponse response = checkStatusCodeAndBodyInGetObjectForPageReq(
                GET_PROJECTS_ENDPOINT,
                HttpStatus.OK.value(),
                SCHEME_SOURCE_PATH + PROJECTS_FOR_PAGE_SCHEME,
                ADMIN_CRED
        );

        List<ProjectDTO> projects = response.extract().jsonPath().getList("projectsForPage", ProjectDTO.class);
        assertEquals(itemsPerPage, projects.size(), "The size of the projects list should be " + itemsPerPage);

        int totalItems = response.extract().jsonPath().getInt(TOTAL_ITEMS_KEY);
        assertEquals(allProjects.size(), totalItems, "The totalItems should be " + allProjects.size());
    }

    @Test
    void whenInvalidReqGetProjects_thenStatus400Test() {
        checkStatusCodeInGetRequest(
                GET_PROJECTS_ENDPOINT,
                HttpStatus.BAD_REQUEST.value(),
                MockConstants.ADMIN_CRED
        );
    }

    @Test
    void whenCreateProject_thanStatus201Test() {
        Project project = MockUtils.generateProjects(1).get(0);
        CreateProjectDTO projectDTO = ProjectMapperForTests.INSTANCE.toCreateProjectDTO(project);
        checkStatusCodeInPostRequest(
                CREATE_PROJECT_FOR_ADMIN_ENDPOINT,
                HttpStatus.CREATED.value(),
                projectDTO,
                MockConstants.ADMIN_CRED
        );
    }

    @Test
    void whenInvalidCreateProject_thanStatus400Test() {
        Project project = MockUtils.generateProjects(1).get(0);
        CreateProjectDTO projectDTO = ProjectMapperForTests.INSTANCE.toCreateProjectDTO(project);
        projectDTO.setName(null);
        checkStatusCodeInPostRequest(
                CREATE_PROJECT_FOR_ADMIN_ENDPOINT,
                HttpStatus.BAD_REQUEST.value(),
                projectDTO,
                MockConstants.ADMIN_CRED
        );
    }

    @Test
    void whenRoleIsNotAdminCreateProject_thanStatus403Test() {
        Project project = MockUtils.generateProjects(1).get(0);
        CreateProjectDTO projectDTO = ProjectMapperForTests.INSTANCE.toCreateProjectDTO(project);
        checkStatusCodeInPostRequest(
                CREATE_PROJECT_FOR_ADMIN_ENDPOINT,
                HttpStatus.FORBIDDEN.value(),
                projectDTO,
                USER_CRED
        );
    }

    @Test
    void whenUpdateProject_thanStatus200Test() {
        Project project = MockUtils.generateProjects(1).get(0);
        projectRepository.save(project);
        UpdateProjectDTO projectDTO = ProjectMapperForTests.INSTANCE.toUpdateProjectDTO(project);
        checkStatusCodeInPostRequest(
                UPDATE_PROJECT_ENDPOINT,
                HttpStatus.OK.value(),
                projectDTO,
                ADMIN_CRED
        );
    }

    @Test
    void whenInvalidRequestUpdateProject_thanStatus400Test() {
        checkStatusCodeInPostRequest(
                UPDATE_PROJECT_ENDPOINT,
                HttpStatus.BAD_REQUEST.value(),
                new UpdateProjectDTO(),
                ADMIN_CRED
        );
    }

    @Test
    void whenRoleIsNotAdminUpdateProject_thanStatus403Test() {
        Project project = MockUtils.generateProjects(1).get(0);
        projectRepository.save(project);
        UpdateProjectDTO projectDTO = ProjectMapperForTests.INSTANCE.toUpdateProjectDTO(project);
        checkStatusCodeInPostRequest(
                CREATE_PROJECT_FOR_ADMIN_ENDPOINT,
                HttpStatus.FORBIDDEN.value(),
                projectDTO,
                USER_CRED
        );
    }

    @Test
    void whenDeleteProject_thanStatus200Test() {
        Project forDelete = MockUtils.generateProjects(1).get(0);
        projectRepository.save(forDelete);
        checkStatusCodeInDeleteRequest(
                DELETE_PROJECT_ENDPOINT,
                HttpStatus.OK.value(),
                forDelete.getId(),
                ADMIN_CRED
        );
    }

    @Test
    void whenBadRequestDeleteProject_thanStatus400Test() {
        checkStatusCodeInDeleteRequest(
                DELETE_PROJECT_ENDPOINT,
                HttpStatus.BAD_REQUEST.value(),
                null,
                ADMIN_CRED
        );
    }

    @Test
    void whenRoleIsNotAdminDeleteProject_thanStatus403Test() {
        Project forDelete = MockUtils.generateProjects(1).get(0);
        projectRepository.save(forDelete);
        checkStatusCodeInDeleteRequest(
                DELETE_PROJECT_ENDPOINT,
                HttpStatus.FORBIDDEN.value(),
                forDelete.getId(),
                USER_CRED
        );
    }
}