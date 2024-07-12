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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tracker/project")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/admin/get/projects")
    public ResponseEntity<ProjectsForPageDTO> getProjects(@Valid GetProjectsForPageDTO req) {
        ProjectsForPageDTO projects = projectService.getProjectsForPage(req);
        return ResponseEntity.ok(projects);
    }

    @PostMapping("/admin/create/project")
    public ResponseEntity<?> createProject(@RequestBody @Valid CreateProjectDTO req) {
        projectService.createProject(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/admin/update/project")
    public ResponseEntity<?> updateProject(@RequestBody @Valid UpdateProjectDTO req) {
        projectService.updateProject(req);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/admin/delete/project")
    public ResponseEntity<?> deleteProject(@RequestParam
                                           @NotNull(message = "Id cannot be null!")
                                           @Min(value = 1, message = "ID cannot be less than 1")
                                           Long id) {
        projectService.deleteProject(id);
        return  ResponseEntity.ok().build();
    }
}
