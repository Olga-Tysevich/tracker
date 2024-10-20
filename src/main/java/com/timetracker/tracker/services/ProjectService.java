package com.timetracker.tracker.services;

import com.timetracker.tracker.entities.Project;
import com.timetracker.tracker.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

/**
 * The interface for managing projects.
 */
public interface ProjectService {

    /**
     * Creates a new project.
     *
     * @param project the project object to save to the database.
     * @see com.timetracker.tracker.entities.Project
     */
    void createProject(Project project);

    /**
     * Deletes a project by its ID.
     *
     * @param id the ID of the project to delete.
     */
    void deleteProject(Long id);

    /**
     * Updates an existing project.
     *
     * @param project the project object to update to the database.
     * @see com.timetracker.tracker.entities.Project
     */
    void updateProject(Project project);

    /**
     * Get the project with the given ID.
     *
     * @param id the ID of the project to get.
     * @return The Optional object that contains or does not contain the specified Project object.
     * @see com.timetracker.tracker.entities.Project
     */
    Optional<Project> getProjectById(Long id);

    /**
     * Retrieves a page of projects.
     *
     * @param req the request parameters for retrieving projects for a page.
     * @return a page containing the projects to display on the page.
     */
    Page<Project> getProjectsForPage(PageRequest req);

}
