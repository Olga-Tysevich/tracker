package com.timetracker.tracker.repositories;

import com.timetracker.tracker.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getByEmail(String email);

    boolean existsByEmail(String email);

    void deleteById(@Nullable Long id);

}
