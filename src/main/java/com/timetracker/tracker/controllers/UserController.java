package com.timetracker.tracker.controllers;

import com.timetracker.tracker.dto.req.CreateUserDTO;
import com.timetracker.tracker.dto.req.GetUsersForPageDTO;
import com.timetracker.tracker.dto.req.UpdateUserDTO;
import com.timetracker.tracker.dto.resp.UsersForPageDTO;
import com.timetracker.tracker.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tracker/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/admin/get")
    public ResponseEntity<UsersForPageDTO> getUsers(@Valid GetUsersForPageDTO req) {
        UsersForPageDTO users = userService.getUsersForPage(req);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/admin/create")
    public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserDTO req) {
        userService.createUser(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UpdateUserDTO req) {
        userService.updateUser(req);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/admin/delete")
    public ResponseEntity<?> deleteUser(@RequestParam
                                            @NotNull(message = "Id cannot be null!")
                                            @Min(value = 1, message = "ID cannot be less than 1")
                                            Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

}
