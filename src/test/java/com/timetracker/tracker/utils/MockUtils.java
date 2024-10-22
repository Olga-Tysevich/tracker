package com.timetracker.tracker.utils;

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

    public static List<User> generateUser(int numberOfObjects) {
        return ehRandom.objects(User.class, numberOfObjects)
                .peek(user -> {
                    user.setRoleSet(Set.of(new Role(2, RoleEnum.ROLE_USER)));
                    user.setEmail(String.format(VALID_EMAIL, user.getEmail().substring(0, 5)));
                    user.setPassword(VALID_PASSWORD);
                })
                .toList();
    }

    public static List<Project> generateProject(int numberOfObjects) {
        return ehRandom.objects(Project.class, numberOfObjects)
                .toList();
    }

    public static List<Record> generateRecord(int numberOfObjects) {
        List<User> users = generateUser(numberOfObjects);
        List<Project> projects = generateProject(numberOfObjects);
        return ehRandom.objects(Record.class, numberOfObjects)
                .peek(record -> {
                    User user = users.get(RANDOM.nextInt(users.size()));
                    Project project = projects.get(RANDOM.nextInt(projects.size()));
                    record.setUser(user);
                    record.setProject(project);
                })
                .toList();
    }

}
