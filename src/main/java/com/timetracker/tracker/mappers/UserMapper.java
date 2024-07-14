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

/**
 * Mapper interface for mapping User entities to UserDTOs and vice versa.
 */
@Mapper
public interface UserMapper {
    /**
     * Instance of the UserMapper interface.
     */
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Maps a CreateUserDTO to a User entity, ignoring the id and roleSet fields.
     * @param userDTO The CreateUserDTO to map to a User entity.
     * @return The mapped User entity.
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "roleSet", ignore = true)
    })
    User toEntity(CreateUserDTO userDTO);

    /**
     * Maps a User entity to a UserDTO, extracting role names from the user's roleSet.
     * @param user The User entity to map to a UserDTO.
     * @return The mapped UserDTO.
     */
    @Mapping(expression = "java(getRoleNames(user))", target = "roles")
    UserDTO toDTO(User user);

    /**
     * Merges the fields of an UpdateUserDTO with an existing User entity.
     * @param user The existing User entity.
     * @param req The UpdateUserDTO containing fields to merge.
     * @return The merged User entity.
     * @throws PasswordMismatchException if password and passwordConfirm do not match.
     */
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

    /**
     * Maps a list of User entities to a UsersForPageDTO with total items count.
     * @param users The list of User entities.
     * @param totalItems The total number of items.
     * @return The mapped UsersForPageDTO.
     */
    default UsersForPageDTO toUserList(List<User> users, Long totalItems) {
        List<UserDTO> result = users.stream()
                .map(INSTANCE::toDTO)
                .toList();
        return new UsersForPageDTO(result, totalItems);
    }

    /**
     * Extracts role names from the User's roleSet.
     * @param user The User entity.
     * @return A set of role names as strings.
     * @throws InvalidRole if the roleSet is null.
     */
    default Set<String> getRoleNames(User user) {
        return Optional.ofNullable(user)
                .map(User::getRoleSet)
                .orElseThrow(InvalidRole::new)
                .stream()
                .map(r -> r.getRole().name())
                .collect(Collectors.toSet());
    }
}
