package com.timetracker.tracker.controllers;

import com.timetracker.tracker.dto.req.CreateUserDTO;
import com.timetracker.tracker.dto.req.GetUsersForPageDTO;
import com.timetracker.tracker.dto.req.UpdateUserDTO;
import com.timetracker.tracker.dto.resp.UserDTO;
import com.timetracker.tracker.dto.resp.UsersForPageDTO;
import com.timetracker.tracker.entities.User;
import com.timetracker.tracker.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.timetracker.tracker.utils.Constants.MIN_ID;

/**
 * Controller class for handling User objects.
 *
 * @see com.timetracker.tracker.entities.User
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tracker/user")
public class UserController {
    /**
     * UserService interface
     *
     * @see com.timetracker.tracker.services.UserService
     */
    private final UserService userService;

    /**
     * Endpoint to get a list of users for a specific page.
     *
     * @param req the request containing parameters for pagination.
     * @return ResponseEntity with UsersForPageDTO object.
     * @see com.timetracker.tracker.dto.req.GetUsersForPageDTO
     */
    @GetMapping("/admin/get")
    public ResponseEntity<UsersForPageDTO> getUsers(@Valid GetUsersForPageDTO req) {
        UsersForPageDTO users = userService.getUsersForPage(req);
        return ResponseEntity.ok(users);
    }

    /**
     * Endpoint to create a new user.
     *
     * @param req the request containing user details.
     * @return ResponseEntity with success status.
     * @see com.timetracker.tracker.dto.req.CreateUserDTO
     */
    @PostMapping("/admin/create")
    public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserDTO req) {
        userService.createUser(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Retrieves the currently authenticated user.
     *
     * @return ResponseEntity containing the UserDTO of the currently authenticated user.
     * @see com.timetracker.tracker.dto.resp.UserDTO
     */
    @GetMapping()
    public ResponseEntity<UserDTO> getUser() {
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        UserDTO userDTO = userService.getUserById(userId);
        return ResponseEntity.ok(userDTO);
    }

    /**
     * Endpoint to update an existing user.
     *
     * @param req the request containing updated user details
     * @return ResponseEntity with success status.
     * @see com.timetracker.tracker.dto.req.UpdateUserDTO
     */
    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UpdateUserDTO req) {
        userService.updateUser(req);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint to delete a user by ID.
     *
     * @param id the ID of the user to be deleted.
     * @return ResponseEntity with success status.
     */
    @DeleteMapping("/admin/delete")
    public ResponseEntity<?> deleteUser(@RequestParam
                                        @NotNull(message = "Id cannot be null!")
                                        @Min(value = MIN_ID, message = "ID cannot be less than 1")
                                        Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

}
