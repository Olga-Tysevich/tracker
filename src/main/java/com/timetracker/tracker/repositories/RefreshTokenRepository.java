package com.timetracker.tracker.repositories;

import com.timetracker.tracker.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface provides methods for accessing and manipulating RefreshToken entities in the database.
 * @see com.timetracker.tracker.entities.RefreshToken
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
}
