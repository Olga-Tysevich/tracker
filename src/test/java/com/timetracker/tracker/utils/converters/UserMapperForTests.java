package com.timetracker.tracker.utils.converters;

import com.timetracker.tracker.dto.req.UpdateUserDTO;
import com.timetracker.tracker.dto.req.UserLoginDTO;
import com.timetracker.tracker.entities.User;
import com.timetracker.tracker.utils.MockConstants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for mapping User entities to UserDTOs and vice versa.
 */
@Mapper
public interface UserMapperForTests {
    /**
     * Instance of the UserMapper interface.
     */
    UserMapperForTests INSTANCE = Mappers.getMapper(UserMapperForTests.class);

    /**
     * Maps a User entity to a UpdateUserDTO, extracting role names from the user's roleSet.
     *
     * @param user The User entity to map to a CreateUserDTO.
     * @return The mapped CreateUserDTO.
     */
    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "surname", target = "surname"),
            @Mapping(source = "password", target = "password"),
            @Mapping(source = "password", target = "passwordConfirm"),
            @Mapping(expression = "java(getRoleNames(user.getRoleSet()))", target = "roleNames")
    })
    UpdateUserDTO toUpdateUserDTO(User user);

    /**
     * Maps a User entity to a valid UserLoginDTO.
     *
     * @param user The User entity to map to a UserLoginDTO.
     * @return The mapped UserLoginDTO.
     */
    @Mappings({
            @Mapping(source = "email", target = "email"),
            @Mapping(expression = "java(\"" + MockConstants.VALID_PASSWORD_DECODED + "\")", target = "password")
    })
    UserLoginDTO toValidUserLoginDTO(User user);

    /**
     * Maps a User entity to an invalid UserLoginDTO.
     *
     * @param user The User entity to map to a UserLoginDTO.
     * @return The mapped UserLoginDTO.
     */
    @Mappings({
            @Mapping(source = "email", target = "email"),
            @Mapping(expression = "java(\"" + MockConstants.INVALID_PASSWORD_DECODED + "\")", target = "password")
    })
    UserLoginDTO toInvalidUserLoginDTO(User user);

}
