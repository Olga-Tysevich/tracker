package com.timetracker.tracker.facades.impl;

import com.timetracker.tracker.dto.req.CreateProjectDTO;
import com.timetracker.tracker.dto.req.GetProjectsForPageDTO;
import com.timetracker.tracker.dto.req.UpdateProjectDTO;
import com.timetracker.tracker.dto.resp.ProjectsForPageDTO;
import com.timetracker.tracker.entities.Project;
import com.timetracker.tracker.exceptions.NotFoundException;
import com.timetracker.tracker.facades.ProjectFacade;
import com.timetracker.tracker.mappers.ProjectMapper;
import com.timetracker.tracker.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

import static com.timetracker.tracker.utils.Constants.REQ_CANNOT_BE_NULL;

/**
 * This class implements the ProjectFacade interface and contains methods for creating, deleting, updating, and retrieving projects.
 *
 * @see com.timetracker.tracker.facades.ProjectFacade
 */
@RequiredArgsConstructor
@Component
public class ProjectFacadeImpl implements ProjectFacade {
    /**
     * ProjectService bean.
     *
     * @see com.timetracker.tracker.services.ProjectService
     */
    private final ProjectService projectService;

    /**
     * Creates a new project based on the provided request.
     *
     * @param req The request object containing project details.
     * @see com.timetracker.tracker.dto.req.CreateProjectDTO
     */
    @Override
    public void createProject(CreateProjectDTO req) {
        if (Objects.isNull(req)) {
            throw new IllegalArgumentException(REQ_CANNOT_BE_NULL);
        }
        Project project = ProjectMapper.INSTANCE.toEntity(req);
        projectService.createProject(project);
    }

    /**
     * Deletes a project by its ID.
     *
     * @param id The ID of the project to be deleted.
     */
    @Override
    public void deleteProject(Long id) {
        Optional.ofNullable(id).ifPresent(projectService::deleteProject);
    }

    /**
     * Updates an existing project based on the provided request.
     *
     * @param req The request object containing the updated project details.
     * @see com.timetracker.tracker.dto.req.UpdateProjectDTO
     */
    @Override
    public void updateProject(UpdateProjectDTO req) {
        if (Objects.isNull(req)) {
            throw new IllegalArgumentException(REQ_CANNOT_BE_NULL);
        }
        Project project = projectService.getProjectById(req.getId())
                .orElseThrow(NotFoundException::new);
        Project forUpdate = ProjectMapper.INSTANCE.mergeReqAndEntity(project, req);
        projectService.updateProject(forUpdate);
    }

    /**
     * Retrieves a list of projects based on the provided request for pagination.
     *
     * @param req The request object containing pagination details.
     * @return The list of projects for the specified page.
     * @see com.timetracker.tracker.dto.req.GetProjectsForPageDTO
     * @see com.timetracker.tracker.dto.resp.ProjectsForPageDTO
     */
    @Override
    public ProjectsForPageDTO getProjectsForPage(GetProjectsForPageDTO req) {
        Page<Project> result = Optional.ofNullable(req)
                .map(r -> PageRequest.of(r.getPageNum(), r.getCountPerPage()))
                .map(projectService::getProjectsForPage)
                .orElseThrow(NotFoundException::new);
        return ProjectMapper.INSTANCE.toProjectList(result.getContent(), result.getTotalElements());
    }

}
