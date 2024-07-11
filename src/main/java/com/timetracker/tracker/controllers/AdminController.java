package com.timetracker.tracker.controllers;

import com.timetracker.tracker.dto.req.CreateProjectDTO;
import com.timetracker.tracker.dto.req.CreateUserDTO;
import com.timetracker.tracker.dto.req.GetUsersForPageDTO;
import com.timetracker.tracker.dto.resp.UsersForPageDTO;
import com.timetracker.tracker.services.ProjectService;
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

    @PostMapping("/create/project")
    public ResponseEntity<?> createProject(@RequestBody @Valid CreateProjectDTO req) {
        projectService.createProject(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
