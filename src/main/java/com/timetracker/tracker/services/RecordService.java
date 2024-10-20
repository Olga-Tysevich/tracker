package com.timetracker.tracker.services;

import com.timetracker.tracker.repositories.specifications.RecordFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.timetracker.tracker.entities.Record;

import java.util.List;
import java.util.Optional;

/**
 * The interface defines methods for managing records.
 */
public interface RecordService {

    /**
     * Creates a new record.
     *
     * @param record The Record object to save to the database.
     * @see com.timetracker.tracker.entities.Record
     */
    void createRecord(Record record);

    /**
     * Deletes the record with the specified ID.
     *
     * @param id The ID of the record to be deleted.
     */
    void deleteRecord(Long id);

    /**
     * Updates an existing record.
     *
     * @param record The Record object to update to the database.
     * @see com.timetracker.tracker.entities.Record
     */
    void updateRecord(Record record);

    /**
     * Retrieves a record by record`s id.
     *
     * @param id The ID of the record.
     * @return The Optional object that contains or does not contain the specified Record object.
     */
    Optional<Record> getRecordById(Long id);

    /**
     * Retrieves a page of records based on the parameters provided in the PageRequest object and the RecordFilter object.
     *
     * @param req The PageRequest object containing the parameters for retrieving records for a page.
     * @param filter The RecordFilter object that contains the filter parameters for retrieving records for the page.
     * @return The page of Record objects.
     * @see com.timetracker.tracker.repositories.specifications.RecordFilter
     */
    Page<Record> getRecordsForPage(PageRequest req, RecordFilter filter);

}
