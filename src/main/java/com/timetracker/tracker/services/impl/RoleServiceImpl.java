package com.timetracker.tracker.services.impl;

import com.timetracker.tracker.entities.Role;
import com.timetracker.tracker.entities.enums.RoleEnum;
import com.timetracker.tracker.repositories.RoleRepository;
import com.timetracker.tracker.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * The class that implements the RoleService interface and provides methods for managing existing roles.
 */
@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    /**
     * RoleRepository bean.
     *
     * @see com.timetracker.tracker.repositories.RoleRepository
     */
    final RoleRepository roleRepository;


    /**
     * Get a set of roles by role names.
     *
     * @param roleNames The set of role names.
     * @return The set of existing roles.
     */
    @Override
    public Set<Role> getRoles(Set<String> roleNames) {
        return roleNames.stream()
                .map(RoleEnum::valueOf)
                .map(roleRepository::getByRole)
                .collect(Collectors.toSet());
    }
}
