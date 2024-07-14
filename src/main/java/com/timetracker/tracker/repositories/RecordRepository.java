package com.timetracker.tracker.repositories;

import com.timetracker.tracker.entities.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.Date;

/**
 * This interface provides methods for accessing and manipulating Record entities in the database.
 * @see com.timetracker.tracker.entities.Record
 */
@Repository
public interface RecordRepository extends JpaRepository<Record, Long>, JpaSpecificationExecutor<Record> {

    /**
     * Retrieves a Record entity by its start date, project id, and user id.
     * @param startDate The start date of the record.
     * @param projectId The id of the project associated with the record.
     * @param userId The id of the user associated with the record.
     * @return The Record entity matching the specified criteria.
     */
    Record getByStartDateAndProjectIdAndUserId(Date startDate, Long projectId, Long userId);

    /**
     * Retrieves a paginated list of Record entities by user id.
     * @param userId The id of the user associated with the records.
     * @param pageRequest The pagination information.
     * @return The Page containing the list of Record entities.
     */
    Page<Record> getAllByUserId(Long userId, PageRequest pageRequest);

    /**
     * Deletes a Record entity by its id.
     * @param id The id of the Record entity to be deleted. Must not be {@literal null}.
     */
    void deleteById(@Nullable Long id);

}
