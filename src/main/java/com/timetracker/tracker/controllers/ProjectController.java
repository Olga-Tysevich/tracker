package com.timetracker.tracker.controllers;

import com.timetracker.tracker.dto.req.CreateProjectDTO;
import com.timetracker.tracker.dto.req.GetProjectsForPageDTO;
import com.timetracker.tracker.dto.req.UpdateProjectDTO;
import com.timetracker.tracker.dto.resp.ProjectsForPageDTO;
import com.timetracker.tracker.services.ProjectService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling Project objects.
 *
 * @see com.timetracker.tracker.entities.Project
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tracker/project")
public class ProjectController {
    /**
     * ProjectService interface
     *
     * @see com.timetracker.tracker.services.ProjectService
     */
    private final ProjectService projectService;

    /**
     * Endpoint to get a list of projects for a specific page.
     *
     * @param req the request containing parameters for pagination.
     * @return ResponseEntity with ProjectsForPageDTO object.
     * @see com.timetracker.tracker.dto.req.GetProjectsForPageDTO
     */
    @GetMapping("/get")
    public ResponseEntity<ProjectsForPageDTO> getProjects(@Valid GetProjectsForPageDTO req) {
        ProjectsForPageDTO projects = projectService.getProjectsForPage(req);
        return ResponseEntity.ok(projects);
    }

    /**
     * Endpoint to create a new project.
     *
     * @param req the request containing project details.
     * @return ResponseEntity with success status.
     * @see com.timetracker.tracker.dto.req.CreateProjectDTO
     */
    @PostMapping("/admin/create")
    public ResponseEntity<?> createProject(@RequestBody @Valid CreateProjectDTO req) {
        projectService.createProject(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Endpoint to update an existing project.
     *
     * @param req the request containing updated project details
     * @return ResponseEntity with success status.
     * @see com.timetracker.tracker.dto.req.UpdateProjectDTO
     */
    @PostMapping("/admin/update")
    public ResponseEntity<?> updateProject(@RequestBody @Valid UpdateProjectDTO req) {
        projectService.updateProject(req);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint to delete a project by ID.
     *
     * @param id the ID of the project to be deleted.
     * @return ResponseEntity with success status.
     */
    @DeleteMapping("/admin/delete")
    public ResponseEntity<?> deleteProject(@RequestParam
                                           @NotNull(message = "Id cannot be null!")
                                           @Min(value = 1, message = "ID cannot be less than 1")
                                           Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok().build();
    }
}
