package com.timetracker.tracker.services.impl;

import com.timetracker.tracker.dto.req.CreateUserDTO;
import com.timetracker.tracker.dto.req.GetUsersForPageDTO;
import com.timetracker.tracker.dto.req.UpdateUserDTO;
import com.timetracker.tracker.dto.resp.UserDTO;
import com.timetracker.tracker.dto.resp.UsersForPageDTO;
import com.timetracker.tracker.entities.Role;
import com.timetracker.tracker.entities.User;
import com.timetracker.tracker.entities.enums.RoleEnum;
import com.timetracker.tracker.exceptions.*;
import com.timetracker.tracker.mappers.UserMapper;
import com.timetracker.tracker.repositories.RoleRepository;
import com.timetracker.tracker.repositories.UserRepository;
import com.timetracker.tracker.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.timetracker.tracker.utils.Constants.REQ_CANNOT_BE_NULL;

/**
 * This class implements the UserService interface and contains methods for creating, deleting, updating, and retrieving users.
 *
 * @see com.timetracker.tracker.services.UserService
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    /**
     * UserRepository bean.
     *
     * @see com.timetracker.tracker.repositories.UserRepository
     */
    private final UserRepository userRepository;
    /**
     * RoleRepository bean.
     *
     * @see com.timetracker.tracker.repositories.RoleRepository
     */
    private final RoleRepository roleRepository;
    /**
     * PasswordEncoder bean.
     *
     * @see com.timetracker.tracker.conf.WebSecurityConfig
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Method to create a new user.
     *
     * @param req The request object containing user information.
     * @throws IllegalArgumentException  if the request is null.
     * @throws UserAlreadyExist          if the user's email already exists in the database.
     * @throws PasswordMismatchException if the password and password confirmation do not match.
     * @throws InvalidRole               if the user's role is invalid.
     * @see com.timetracker.tracker.dto.req.CreateUserDTO
     */
    @Override
    public void createUser(CreateUserDTO req) {
        if (Objects.isNull(req)) {
            throw new IllegalArgumentException(REQ_CANNOT_BE_NULL);
        }
        checkPassword(req.getPassword(), req.getPasswordConfirm());
        User user = UserMapper.INSTANCE.toEntity(req);
        checkEmail(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        setRoles(user, req.getRoleNames());
        userRepository.save(user);
    }

    /**
     * Method to delete a user by id.
     *
     * @param id The id of the user to be deleted.
     */
    @Override
    public void deleteUser(Long id) {
        Optional.ofNullable(id).ifPresent(userRepository::deleteById);
    }

    /**
     * Method to update an existing user.
     *
     * @param req The request object containing user information to be updated.
     * @throws IllegalArgumentException if the request is null.
     * @throws NotFoundException        if the user is not found.
     * @see com.timetracker.tracker.dto.req.UpdateUserDTO
     */
    @Override
    public void updateUser(UpdateUserDTO req) {
        if (Objects.isNull(req)) {
            throw new IllegalArgumentException(REQ_CANNOT_BE_NULL);
        }
        User user = userRepository.findById(req.getId())
                .orElseThrow(NotFoundException::new);
        User forUpdate = UserMapper.INSTANCE.mergeReqAndEntity(user, req);
        if (Objects.nonNull(req.getRoleNames()) && !req.getRoleNames().isEmpty()) {
            setRoles(forUpdate, req.getRoleNames());
        }
        userRepository.save(forUpdate);
    }

    /**
     * Method to retrieve a user by id.
     *
     * @param id The id of the user to retrieve.
     * @return The user DTO object.
     * @throws UserNotFoundException if the user is not found.
     * @see com.timetracker.tracker.dto.resp.UserDTO
     */
    @Override
    public UserDTO getUserById(Long id) {
        return Optional.ofNullable(id)
                .map(userRepository::findById)
                .orElseThrow(UserNotFoundException::new)
                .map(UserMapper.INSTANCE::toDTO)
                .orElse(null);
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
    public UsersForPageDTO getUsersForPage(GetUsersForPageDTO req) {
        Page<User> result = Optional.ofNullable(req)
                .map(r -> PageRequest.of(r.getPageNum(), r.getCountPerPage()))
                .map(userRepository::findAll)
                .orElseThrow(NotFoundException::new);
        return UserMapper.INSTANCE.toUserList(result.getContent(), result.getTotalElements());
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

    /**
     * Method to check if the password and password confirmation match.
     *
     * @param password        The user's password.
     * @param passwordConfirm The password confirmation.
     * @throws PasswordMismatchException if the password and password confirmation do not match.
     */
    private void checkPassword(String password, String passwordConfirm) {
        if (!Objects.requireNonNull(password).equals(passwordConfirm)) {
            throw new PasswordMismatchException();
        }
    }

    /**
     * Method to set roles for the user based on the role names provided.
     *
     * @param user      The user entity to set the roles.
     * @param roleNames The set of role names to set for the user.
     * @throws InvalidRole if the user's role is invalid.
     * @see com.timetracker.tracker.entities.User
     * @see com.timetracker.tracker.entities.Role
     */
    private void setRoles(User user, Set<String> roleNames) {
        if (Objects.nonNull(roleNames) & !roleNames.isEmpty()) {
            Set<Role> roles = roleNames.stream()
                    .map(RoleEnum::valueOf)
                    .map(roleRepository::getByRole)
                    .collect(Collectors.toSet());
            user.setRoleSet(roles);
            return;
        }
        throw new InvalidRole();
    }
}
