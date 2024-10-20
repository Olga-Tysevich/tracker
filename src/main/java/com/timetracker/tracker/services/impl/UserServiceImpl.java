package com.timetracker.tracker.services.impl;

import com.timetracker.tracker.entities.User;
import com.timetracker.tracker.exceptions.*;
import com.timetracker.tracker.repositories.UserRepository;
import com.timetracker.tracker.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * This class implements the UserService interface and contains methods for creating, deleting, updating, and retrieving users.
 *
 * @see com.timetracker.tracker.services.UserService
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {
    /**
     * UserRepository bean.
     *
     * @see com.timetracker.tracker.repositories.UserRepository
     */
    private final UserRepository userRepository;
    /**
     * PasswordEncoder bean.
     *
     * @see com.timetracker.tracker.conf.WebSecurityConfig
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Method to create a new user.
     *
     * @param user the user object to save to the database.
     * @throws IllegalArgumentException  if the request is null.
     * @throws UserAlreadyExist          if the user's email already exists in the database.
     * @throws PasswordMismatchException if the password and password confirmation do not match.
     * @throws InvalidRole               if the user's role is invalid.
     * @see com.timetracker.tracker.entities.User
     */
    @Override
    public void createUser(User user) {
        checkEmail(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    /**
     * Method to delete a user by id.
     *
     * @param id The id of the user to be deleted.
     */
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Method to update an existing user.
     *
     * @param user the user object to update to the database.
     * @throws IllegalArgumentException if the request is null.
     * @throws NotFoundException        if the user is not found.
     * @see com.timetracker.tracker.entities.User
     */
    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    /**
     * Method to retrieve a user by id.
     *
     * @param id The id of the user to retrieve.
     * @return The Optional object that contains or does not contain the specified User object.
     * @see com.timetracker.tracker.entities.User
     */
    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Method to retrieve a list of users for a specified page.
     *
     * @param req The request object containing pagination information.
     * @return The response object containing user list for the page.
     * @throws NotFoundException if the page is not found.
     * @see com.timetracker.tracker.dto.resp.UsersForPageDTO
     */
    @Override
    public Page<User> getUsersForPage(PageRequest req) {
        return userRepository.findAll(req);
    }

    /**
     * Method to check if the user's email already exists in the database.
     *
     * @param user The user entity to check the email.
     * @throws UserAlreadyExist if the user's email already exists in the database.
     * @see com.timetracker.tracker.entities.User
     */
    private void checkEmail(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExist();
        }
    }


}
