package com.timetracker.tracker.repositories;

import com.timetracker.tracker.entities.Role;
import com.timetracker.tracker.entities.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role getByRole(RoleEnum role);

}
