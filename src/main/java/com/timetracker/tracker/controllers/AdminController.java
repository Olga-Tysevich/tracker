package com.timetracker.tracker.controllers;

import com.timetracker.tracker.dto.req.*;
import com.timetracker.tracker.dto.resp.ProjectsForPageDTO;
import com.timetracker.tracker.dto.resp.RecordsForPageDTO;
import com.timetracker.tracker.dto.resp.UsersForPageDTO;
import com.timetracker.tracker.services.ProjectService;
import com.timetracker.tracker.services.RecordService;
import com.timetracker.tracker.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tracker/admin")
public class AdminController {
    private final UserService userService;
    private final ProjectService projectService;
    private final RecordService recordService;

    @GetMapping("/get/users")
    public ResponseEntity<UsersForPageDTO> getUsers(@Valid GetUsersForPageDTO req) {
        UsersForPageDTO users = userService.getUsersForPage(req);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/create/user")
    public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserDTO req) {
        userService.createUser(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get/projects")
    public ResponseEntity<ProjectsForPageDTO> getProjects(@Valid GetProjectsForPageDTO req) {
        ProjectsForPageDTO projects = projectService.getProjectsForPage(req);
        return ResponseEntity.ok(projects);
    }

    @PostMapping("/create/project")
    public ResponseEntity<?> createProject(@RequestBody @Valid CreateProjectDTO req) {
        projectService.createProject(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get/records")
    public ResponseEntity<RecordsForPageDTO> getRecords(@Valid GetRecordsForPageDTO req) {
        RecordsForPageDTO records = recordService.getRecordsForPage(req);
        return ResponseEntity.ok(records);
    }

}
