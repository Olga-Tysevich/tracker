package com.timetracker.tracker.services.impl;

import com.timetracker.tracker.entities.Record;
import com.timetracker.tracker.exceptions.NotFoundException;
import com.timetracker.tracker.repositories.RecordRepository;
import com.timetracker.tracker.repositories.specifications.RecordFilter;
import com.timetracker.tracker.repositories.specifications.RecordSpecification;
import com.timetracker.tracker.services.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * This class implements the RecordService interface and contains methods for creating, deleting, updating, and retrieving records.
 *
 * @see com.timetracker.tracker.services.RecordService
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class RecordServiceImpl implements RecordService {
    /**
     * RecordRepository bean.
     *
     * @see com.timetracker.tracker.repositories.RecordRepository
     */
    private final RecordRepository recordRepository;

    /**
     * Creates a new record.
     *
     * @param record The Record object to save to the database.
     * @throws IllegalArgumentException if the request is {@literal null}.
     * @see com.timetracker.tracker.entities.Record
     */
    @Override
    public void createRecord(Record record) {
        checkRecord(record);
        recordRepository.save(record);
    }

    /**
     * Deletes a record by its ID.
     *
     * @param id the ID of the record to delete.
     */
    @Override
    public void deleteRecord(Long id) {
        recordRepository.deleteById(id);
    }

    /**
     * Updates an existing record.
     *
     * @param record The Record object to update to the database.
     * @throws IllegalArgumentException if the request is {@literal null}.
     * @see com.timetracker.tracker.entities.Record
     */
    @Override
    public void updateRecord(Record record) {
        recordRepository.save(record);
    }

    @Override
    public Optional<Record> getRecordById(Long id) {
        return recordRepository.findById(id);
    }

    /**
     * Retrieves a page of records.
     *
     * @param req    The PageRequest object containing the parameters for retrieving records for a page.
     * @param filter The RecordFilter object that contains the filter parameters for retrieving records for the page.
     * @return the page of records.
     * @throws NotFoundException if the request is {@literal null}.
     * @see com.timetracker.tracker.repositories.specifications.RecordFilter
     */
    @Override
    public Page<Record> getRecordsForPage(PageRequest req, RecordFilter filter) {
        return recordRepository.findAll(RecordSpecification.search(filter), req);
    }

    /**
     * Checks if a record already exists with the same start date, project ID, and user ID, and updates it.
     *
     * @param record the record to check.
     * @see com.timetracker.tracker.entities.Record
     */
    private void checkRecord(Record record) {
        Record exist = recordRepository.getByStartDateAndProjectIdAndUserId(record.getStartDate(),
                record.getProject().getId(), record.getUser().getId());
        if (Objects.isNull(exist)) {
            return;
        }
        exist.setStartDate(record.getStartDate());
        exist.setDuration(record.getDuration());
    }
}
