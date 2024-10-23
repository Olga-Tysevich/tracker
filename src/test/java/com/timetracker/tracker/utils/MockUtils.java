package com.timetracker.tracker.utils;

import com.timetracker.tracker.dto.req.CreateUserDTO;
import com.timetracker.tracker.dto.resp.UserDTO;
import com.timetracker.tracker.entities.Project;
import com.timetracker.tracker.entities.Record;
import com.timetracker.tracker.entities.Role;
import com.timetracker.tracker.entities.User;
import com.timetracker.tracker.entities.enums.RoleEnum;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.FieldPredicates;
import io.github.benas.randombeans.api.EnhancedRandom;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

import static com.timetracker.tracker.utils.MockConstants.*;

public class MockUtils {
    private static final EnhancedRandom ehRandom;

    static {
        ehRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
                .seed(123L)
                .objectPoolSize(20)
                .randomizationDepth(3)
                .charset(StandardCharsets.UTF_8)
                .stringLengthRange(5, 50)
                .collectionSizeRange(1, NUMBER_OF_OBJECTS)
                .scanClasspathForConcreteTypes(true)
                .overrideDefaultInitialization(false)
                .excludeField(FieldPredicates.named("id"))
                .build();
    }

    public static User getAdmin() {
        return User.builder()
                .name(ADMIN_EMAIL)
                .surname(ADMIN_EMAIL)
                .email(ADMIN_EMAIL)
                .password(VALID_PASSWORD)
                .roleSet(Set.of(new Role(1, RoleEnum.ROLE_ADMIN)))
                .build();
    }

    public static User getUser() {
        return User.builder()
                .name(VALID_USER_NAME)
                .surname(VALID_USER_SURNAME)
                .email(USER_EMAIL)
                .password(VALID_PASSWORD)
                .roleSet(Set.of(new Role(2, RoleEnum.ROLE_USER)))
                .build();
    }

    public static List<User> generateUsers(int numberOfObjects) {
        return ehRandom.objects(User.class, numberOfObjects)
                .peek(user -> {
                    user.setRoleSet(Set.of(new Role(2, RoleEnum.ROLE_USER)));
                    user.setEmail(String.format(VALID_EMAIL, user.getEmail().substring(0, 5)));
                    user.setPassword(VALID_PASSWORD);
                })
                .toList();
    }

    public static List<Project> generateProjects(int numberOfObjects) {
        return ehRandom.objects(Project.class, numberOfObjects)
                .toList();
    }

    public static List<Record> generateRecords(int numberOfObjects) {
        List<User> users = generateUsers(numberOfObjects);
        List<Project> projects = generateProjects(numberOfObjects);
        return ehRandom.objects(Record.class, numberOfObjects)
                .peek(record -> {
                    User user = users.get(RANDOM.nextInt(users.size()));
                    Project project = projects.get(RANDOM.nextInt(projects.size()));
                    record.setUser(user);
                    record.setProject(project);
                })
                .toList();
    }

    public static CreateUserDTO getCreateUserDTO() {
        return CreateUserDTO.builder()
                .name(VALID_USER_NAME)
                .surname(VALID_USER_SURNAME)
                .email(VALID_USER_EMAIL)
                .password(VALID_PASSWORD)
                .passwordConfirm(VALID_PASSWORD)
                .roleNames(Set.of(RoleEnum.ROLE_USER.name()))
                .build();
    }

}
