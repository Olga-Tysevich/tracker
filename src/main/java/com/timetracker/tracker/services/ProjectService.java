package com.timetracker.tracker.services;

import com.timetracker.tracker.dto.req.CreateProjectDTO;
import com.timetracker.tracker.dto.req.GetProjectsForPageDTO;
import com.timetracker.tracker.dto.req.UpdateProjectDTO;
import com.timetracker.tracker.dto.resp.ProjectsForPageDTO;

/**
 * The interface for managing projects.
 */
public interface ProjectService {

    /**
     * Creates a new project.
     *
     * @param req the request body for creating a project.
     * @see com.timetracker.tracker.dto.req.CreateProjectDTO
     */
    void createProject(CreateProjectDTO req);

    /**
     * Deletes a project by its ID.
     *
     * @param id the ID of the project to delete.
     */
    void deleteProject(Long id);

    /**
     * Updates an existing project.
     *
     * @param req the request body for updating a project.
     * @see com.timetracker.tracker.dto.req.UpdateProjectDTO
     */
    void updateProject(UpdateProjectDTO req);

    /**
     * Retrieves a list of projects for a specific page.
     *
     * @param req the request parameters for retrieving projects for a page.
     * @return a DTO containing the projects to display on the page.
     * @see com.timetracker.tracker.dto.req.GetProjectsForPageDTO
     * @see com.timetracker.tracker.dto.resp.ProjectsForPageDTO
     */
    ProjectsForPageDTO getProjectsForPage(GetProjectsForPageDTO req);

}
