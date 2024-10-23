package com.timetracker.tracker.repositories;

import com.timetracker.tracker.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This interface provides methods for accessing and manipulating User entities in the database.
 *
 * @see com.timetracker.tracker.entities.User
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieves a user by their email address.
     *
     * @param email The email address of the user to retrieve.
     * @return An Optional containing the user with the specified email, if found.
     */
    Optional<User> getByEmail(String email);

    /**
     * Checks if a user exists with the given email address.
     *
     * @param email The email address to check.
     * @return true if a user with the email address exists, false otherwise.
     */
    boolean existsByEmail(String email);

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete. Must not be {@literal null}.
     */
    void deleteById(@Nullable Long id);

}
