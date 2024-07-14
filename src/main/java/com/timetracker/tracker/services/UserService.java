package com.timetracker.tracker.services;

import com.timetracker.tracker.dto.req.CreateUserDTO;
import com.timetracker.tracker.dto.req.GetUsersForPageDTO;
import com.timetracker.tracker.dto.req.UpdateUserDTO;
import com.timetracker.tracker.dto.resp.UserDTO;
import com.timetracker.tracker.dto.resp.UsersForPageDTO;

/**
 * The interface provides methods to interact with user data.
 */
public interface UserService {

    /**
     * Create a new user with the given request data.
     *
     * @param req the request data to create a new user.
     * @see com.timetracker.tracker.dto.req.CreateUserDTO
     */
    void createUser(CreateUserDTO req);

    /**
     * Delete the user with the given ID.
     *
     * @param id the ID of the user to delete.
     */
    void deleteUser(Long id);

    /**
     * Update the user with the given request data.
     *
     * @param req the request data to update a user.
     * @see com.timetracker.tracker.dto.req.UpdateUserDTO
     */
    void updateUser(UpdateUserDTO req);

    /**
     * et the user with the given ID.
     *
     * @param id the ID of the user to get.
     * @return the user data.
     * @see com.timetracker.tracker.dto.resp.UserDTO
     */
    UserDTO getUserById(Long id);

    /**
     * Get a list of users for a specific page with the given request data.
     *
     * @param req the request data to get users for a page.
     * @return the list of users for the page.
     * @see com.timetracker.tracker.dto.req.GetUsersForPageDTO
     * @see com.timetracker.tracker.dto.resp.UsersForPageDTO
     */
    UsersForPageDTO getUsersForPage(GetUsersForPageDTO req);

}
