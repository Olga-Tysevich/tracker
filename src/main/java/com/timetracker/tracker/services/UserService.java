package com.timetracker.tracker.services;

import com.timetracker.tracker.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

/**
 * The interface provides methods to interact with user data.
 */
public interface UserService {

    /**
     * Create a new user.
     *
     * @param user the user object to save to the database.
     * @see com.timetracker.tracker.entities.User
     */
    void createUser(User user);

    /**
     * Delete the user with the given ID.
     *
     * @param id the ID of the user to delete.
     */
    void deleteUser(Long id);

    /**
     * Updates an existing user.
     *
     * @param user the user object to update to the database.
     * @see com.timetracker.tracker.entities.User
     */
    void updateUser(User user);

    /**
     * Get the user with the given ID.
     *
     * @param id the ID of the user to get.
     * @return The Optional object that contains or does not contain the specified User object.
     * @see com.timetracker.tracker.entities.User
     */
    Optional<User> getUserById(Long id);

    /**
     * Get the user with the given email.
     *
     * @param email the email of the user to get.
     * @return The Optional object that contains or does not contain the specified User object.
     * @see com.timetracker.tracker.entities.User
     */
    Optional<User> getUserByEmail(String email);

    /**
     * Get a page of users for a specific page with the given request data.
     *
     * @param req the request data to get users for a page.
     * @return the page of users.
     */
    Page<User> getUsersForPage(PageRequest req);

}
