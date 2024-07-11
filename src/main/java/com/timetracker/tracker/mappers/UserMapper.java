package com.timetracker.tracker.mappers;

import com.timetracker.tracker.dto.req.CreateUserDTO;
import com.timetracker.tracker.dto.resp.UserDTO;
import com.timetracker.tracker.dto.resp.UsersForPageDTO;
import com.timetracker.tracker.entities.User;
import com.timetracker.tracker.exceptions.InvalidRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "roleSet", ignore = true)
    User toEntity(CreateUserDTO userDTO);


    @Mapping(expression = "java(getRoleNames(user))", target = "roles")
    UserDTO toDTO(User user);

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
