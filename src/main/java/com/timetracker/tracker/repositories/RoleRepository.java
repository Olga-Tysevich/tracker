package com.timetracker.tracker.repositories;

import com.timetracker.tracker.entities.Role;
import com.timetracker.tracker.entities.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface provides methods for accessing and manipulating Role entities in the database.
 *
 * @see com.timetracker.tracker.entities.Role
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Retrieves a Role entity by RoleEnum value.
     *
     * @param role The role of the user associated with the role enum.
     * @return The user Role from database.
     */
    Role getByRole(RoleEnum role);
}
