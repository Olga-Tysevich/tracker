package com.timetracker.tracker.services.impl;

import com.timetracker.tracker.dto.req.CreateProjectDTO;
import com.timetracker.tracker.dto.req.GetProjectsForPageDTO;
import com.timetracker.tracker.dto.req.UpdateProjectDTO;
import com.timetracker.tracker.dto.resp.ProjectsForPageDTO;
import com.timetracker.tracker.entities.Project;
import com.timetracker.tracker.exceptions.NotFoundException;
import com.timetracker.tracker.exceptions.ObjectAlreadyExist;
import com.timetracker.tracker.mappers.ProjectMapper;
import com.timetracker.tracker.repositories.ProjectRepository;
import com.timetracker.tracker.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static com.timetracker.tracker.utils.Constants.PROJECT_ALREADY_EXIST;
import static com.timetracker.tracker.utils.Constants.REQ_CANNOT_BE_NULL;

/**
 * This class implements the ProjectService interface and contains methods for creating, deleting, updating, and retrieving projects.
 *
 * @see com.timetracker.tracker.services.ProjectService
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class ProjectServiceImpl implements ProjectService {
    /**
     * ProjectRepository bean.
     *
     * @see com.timetracker.tracker.repositories.ProjectRepository
     */
    private final ProjectRepository projectRepository;

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
        checkProjectName(project);
        projectRepository.save(project);
    }

    /**
     * Deletes a project by its ID.
     *
     * @param id The ID of the project to be deleted.
     */
    @Override
    public void deleteProject(Long id) {
        Optional.ofNullable(id).ifPresent(projectRepository::deleteById);
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
        Project project = projectRepository.findById(req.getId())
                .orElseThrow(NotFoundException::new);
        Project forUpdate = ProjectMapper.INSTANCE.mergeReqAndEntity(project, req);
        projectRepository.save(forUpdate);
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
                .map(projectRepository::findAll)
                .orElseThrow(NotFoundException::new);
        return ProjectMapper.INSTANCE.toProjectList(result.getContent(), result.getTotalElements());
    }

    /**
     * Checks if a project with the same name already exists in the database and throws an exception if it does.
     *
     * @param project The project to check for duplicate name.
     * @see com.timetracker.tracker.entities.Project
     */
    private void checkProjectName(Project project) {
        if (projectRepository.existsByName(project.getName())) {
            throw new ObjectAlreadyExist(PROJECT_ALREADY_EXIST);
        }
    }
}
