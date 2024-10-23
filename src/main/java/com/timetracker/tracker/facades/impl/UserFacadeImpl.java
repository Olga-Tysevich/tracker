package com.timetracker.tracker.facades.impl;

import com.timetracker.tracker.dto.req.CreateUserDTO;
import com.timetracker.tracker.dto.req.GetUsersForPageDTO;
import com.timetracker.tracker.dto.req.UpdateUserDTO;
import com.timetracker.tracker.dto.resp.UserDTO;
import com.timetracker.tracker.dto.resp.UsersForPageDTO;
import com.timetracker.tracker.entities.Role;
import com.timetracker.tracker.entities.User;
import com.timetracker.tracker.exceptions.InvalidRole;
import com.timetracker.tracker.exceptions.NotFoundException;
import com.timetracker.tracker.exceptions.PasswordMismatchException;
import com.timetracker.tracker.exceptions.UserNotFoundException;
import com.timetracker.tracker.facades.UserFacade;
import com.timetracker.tracker.mappers.UserMapper;
import com.timetracker.tracker.services.RoleService;
import com.timetracker.tracker.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.timetracker.tracker.utils.Constants.REQ_CANNOT_BE_NULL;

/**
 * This class implements the UserFacade interface and contains methods for creating, deleting, updating, and retrieving users.
 *
 * @see com.timetracker.tracker.facades.UserFacade
 */

@RequiredArgsConstructor
@Component
public class UserFacadeImpl implements UserFacade {
    /**
     * The UserService bean.
     *
     * @see com.timetracker.tracker.services.UserService
     */
    private final UserService userService;
    /**
     * The RoleService bean.
     *
     * @see com.timetracker.tracker.services.RoleService
     */
    private final RoleService roleService;

    @Override
    public void createUser(CreateUserDTO req) {
        if (Objects.isNull(req)) {
            throw new IllegalArgumentException(REQ_CANNOT_BE_NULL);
        }
        checkPassword(req.getPassword(), req.getPasswordConfirm());
        User user = UserMapper.INSTANCE.toEntity(req);
        setRoles(user, req.getRoleNames());
        userService.createUser(user);
    }

    /**
     * Method to delete a user by id.
     *
     * @param id The id of the user to be deleted.
     */
    @Override
    public void deleteUser(Long id) {
        Optional.ofNullable(id).ifPresent(userService::deleteUser);
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
        User user = userService.getUserById(req.getId())
                .orElseThrow(NotFoundException::new);
        User forUpdate = UserMapper.INSTANCE.mergeReqAndEntity(user, req);
        if (Objects.nonNull(req.getRoleNames()) && !req.getRoleNames().isEmpty()) {
            setRoles(forUpdate, req.getRoleNames());
        }
        userService.updateUser(forUpdate);
    }

    /**
     * Method to retrieve current user from SecurityContextHolder.
     *
     * @return The user DTO object.
     * @throws UserNotFoundException if the user is not found.
     * @see com.timetracker.tracker.dto.resp.UserDTO
     */
    @Override
    public UserDTO getCurrentUser() {
        Long id = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return Optional.ofNullable(id)
                .map(userService::getUserById)
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
                .map(userService::getUsersForPage)
                .orElseThrow(NotFoundException::new);
        return UserMapper.INSTANCE.toUserList(result.getContent(), result.getTotalElements());
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
            Set<Role> roles = roleService.getRoles(roleNames);
            user.setRoleSet(roles);
            return;
        }
        throw new InvalidRole();
    }

}
