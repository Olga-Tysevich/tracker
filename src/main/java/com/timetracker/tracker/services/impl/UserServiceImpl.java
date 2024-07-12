package com.timetracker.tracker.services.impl;

import com.timetracker.tracker.dto.req.CreateUserDTO;
import com.timetracker.tracker.dto.req.GetUsersForPageDTO;
import com.timetracker.tracker.dto.req.UpdateUserDTO;
import com.timetracker.tracker.dto.resp.UserDTO;
import com.timetracker.tracker.dto.resp.UsersForPageDTO;
import com.timetracker.tracker.entities.Role;
import com.timetracker.tracker.entities.User;
import com.timetracker.tracker.entities.enums.RoleEnum;
import com.timetracker.tracker.exceptions.InvalidRole;
import com.timetracker.tracker.exceptions.NotFoundException;
import com.timetracker.tracker.exceptions.UserAlreadyExist;
import com.timetracker.tracker.exceptions.UserNotFoundException;
import com.timetracker.tracker.mappers.UserMapper;
import com.timetracker.tracker.repositories.RoleRepository;
import com.timetracker.tracker.repositories.UserRepository;
import com.timetracker.tracker.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void createUser(CreateUserDTO req) {
        Optional.ofNullable(req)
                .map(UserMapper.INSTANCE::toEntity)
                .map(this::checkEmail)
                .map(u -> setRoles(u, req.getRoleNames()))
                .ifPresent(userRepository::save);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(UpdateUserDTO req) {
        User user = Optional.ofNullable(req)
                .flatMap(r -> userRepository.findById(r.getId()))
                .orElseThrow(NotFoundException::new);
        User forUpdate = UserMapper.INSTANCE.mergeReqAndEntity(user, req);
        setRoles(forUpdate, req.getRoleNames());
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

    private User checkEmail(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExist();
        }
        return user;
    }

    private User setRoles(User user, Set<String> roleNames) {
        if (Objects.nonNull(roleNames) & !roleNames.isEmpty()) {
            Set<Role> roles = roleNames.stream()
                    .map(RoleEnum::valueOf)
                    .map(roleRepository::getByRole)
                    .collect(Collectors.toSet());
            user.setRoleSet(roles);
            return user;
        }
        throw new InvalidRole();
    }
}
