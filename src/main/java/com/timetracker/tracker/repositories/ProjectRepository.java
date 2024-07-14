package com.timetracker.tracker.repositories;

import com.timetracker.tracker.entities.Project;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

/**
 * This interface provides methods for accessing and manipulating Project entities in the database.
 * @see com.timetracker.tracker.entities.Project
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * Checks if a Project with the given name exists in the database.
     * @param name The name of the Project to check.
     * @return true if a Project with the given name exists, false otherwise.
     */
    boolean existsByName(String name);

    /**
     * Deletes a Project with the given id from the database.
     * @param id The id of the Project to delete. Must not be {@literal null}.
     */
    void deleteById(@Nullable Long id);

}
