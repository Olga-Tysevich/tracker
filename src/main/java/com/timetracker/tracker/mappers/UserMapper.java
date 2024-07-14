package com.timetracker.tracker.mappers;

import com.timetracker.tracker.dto.req.CreateUserDTO;
import com.timetracker.tracker.dto.req.UpdateUserDTO;
import com.timetracker.tracker.dto.resp.UserDTO;
import com.timetracker.tracker.dto.resp.UsersForPageDTO;
import com.timetracker.tracker.entities.User;
import com.timetracker.tracker.exceptions.InvalidRole;
import com.timetracker.tracker.exceptions.PasswordMismatchException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "roleSet", ignore = true)
    })
    User toEntity(CreateUserDTO userDTO);


    @Mapping(expression = "java(getRoleNames(user))", target = "roles")
    UserDTO toDTO(User user);

    default User mergeReqAndEntity(User user, UpdateUserDTO req) {
        if (Objects.nonNull(req.getName())) {
            user.setName(req.getName());
        }
        if (Objects.nonNull(req.getSurname())) {
            user.setSurname(req.getSurname());
        }
        if (Objects.nonNull(req.getPassword()) & Objects.nonNull(req.getPasswordConfirm())) {
            if (!req.getPassword().equals(req.getPasswordConfirm())) {
                throw new PasswordMismatchException();
            }
            user.setPassword(req.getPassword());
        }
        return user;
    }

    default UsersForPageDTO toUserList(List<User> users, Long totalItems) {
        List<UserDTO> result = users.stream()
                .map(INSTANCE::toDTO)
                .toList();
        return new UsersForPageDTO(result, totalItems);
    }

    default Set<String> getRoleNames(User user) {
        return Optional.ofNullable(user)
                .map(User::getRoleSet)
                .orElseThrow(InvalidRole::new)
                .stream()
                .map(r -> r.getRole().name())
                .collect(Collectors.toSet());
    }
}
