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
 *
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

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

    @Override
    public void deleteUser(Long id) {
        Optional.ofNullable(id).ifPresent(userRepository::deleteById);
    }

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

    @Override
    public UserDTO getUserById(Long id) {
        return Optional.ofNullable(id)
                .map(userRepository::findById)
                .orElseThrow(UserNotFoundException::new)
                .map(UserMapper.INSTANCE::toDTO)
                .orElse(null);
    }

    @Override
    public UsersForPageDTO getUsersForPage(GetUsersForPageDTO req) {
        Page<User> result = Optional.ofNullable(req)
                .map(r -> PageRequest.of(r.getPageNum(), r.getCountPerPage()))
                .map(userRepository::findAll)
                .orElseThrow(NotFoundException::new);
        return UserMapper.INSTANCE.toUserList(result.getContent(), result.getTotalElements());
    }

    private void checkEmail(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExist();
        }
    }

    private void checkPassword(String password, String passwordConfirm) {
        if (!Objects.requireNonNull(password).equals(passwordConfirm)) {
            throw new PasswordMismatchException();
        }
    }


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
