package com.timetracker.tracker.services;

import com.timetracker.tracker.entities.Role;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * The interface provides methods to interact with existing roles.
 */
@Service
public interface RoleService {

    /**
     * Get a set of roles by role names.
     * @param roleNames The set of role names.
     * @return The set of existing roles.
     */
    Set<Role> getRoles(Set<String> roleNames);
}
