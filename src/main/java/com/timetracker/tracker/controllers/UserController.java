package com.timetracker.tracker.controllers;

import com.timetracker.tracker.dto.resp.UsersForPageDTO;
import com.timetracker.tracker.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tracker/user")
public class UserController {
    private final UserService userService;

}
