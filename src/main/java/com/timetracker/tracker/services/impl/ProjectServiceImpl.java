package com.timetracker.tracker.services.impl;

import com.timetracker.tracker.entities.Project;
import com.timetracker.tracker.exceptions.ObjectAlreadyExist;
import com.timetracker.tracker.repositories.ProjectRepository;
import com.timetracker.tracker.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.timetracker.tracker.utils.Constants.PROJECT_ALREADY_EXIST;

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
     * @param project the project object to save to the database.
     * @see com.timetracker.tracker.entities.Project
     */
    @Override
    public void createProject(Project project) {
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
        projectRepository.deleteById(id);
    }

    /**
     * Updates an existing project based on the provided request.
     *
     * @param project the project object to update to the database.
     * @see com.timetracker.tracker.entities.Project
     */
    @Override
    public void updateProject(Project project) {
        projectRepository.save(project);
    }

    /**
     * Method to retrieve a project by id.
     *
     * @param id The id of the project to retrieve.
     * @return The Optional object that contains or does not contain the specified Project object.
     * @see com.timetracker.tracker.entities.Project
     */
    @Override
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    /**
     * Retrieves a page of projects based on the provided request for pagination.
     *
     * @param req The request object containing pagination details.
     * @return The page of projects for the specified page.
     */
    @Override
    public Page<Project> getProjectsForPage(PageRequest req) {
        return projectRepository.findAll(req);
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
