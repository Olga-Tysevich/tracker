package com.timetracker.tracker.services;

import com.timetracker.tracker.dto.req.CreateRecordDTO;
import com.timetracker.tracker.dto.req.GetRecordsForPageDTO;
import com.timetracker.tracker.dto.req.UpdateRecordDTO;
import com.timetracker.tracker.dto.resp.RecordsForPageDTO;

/**
 * The interface defines methods for managing records.
 */
public interface RecordService {

    /**
     * Creates a new record based on the information provided in the CreateRecordDTO object.
     *
     * @param req The CreateRecordDTO object containing the information for the new record.
     * @see com.timetracker.tracker.dto.req.CreateRecordDTO
     */
    void createRecord(CreateRecordDTO req);

    /**
     * Deletes the record with the specified ID.
     *
     * @param id The ID of the record to be deleted.
     */
    void deleteRecord(Long id);

    /**
     * Updates an existing record with the information provided in the UpdateRecordDTO object.
     *
     * @param req The UpdateRecordDTO object containing the updated information for the record.
     * @see com.timetracker.tracker.dto.req.UpdateRecordDTO
     */
    void updateRecord(UpdateRecordDTO req);

    /**
     * Retrieves records for a specific page based on the parameters provided in the GetRecordsForPageDTO object.
     *
     * @param req The GetRecordsForPageDTO object containing the parameters for retrieving records for a page.
     * @return A RecordsForPageDTO object containing the records for the specified page.
     * @see com.timetracker.tracker.dto.req.GetRecordsForPageDTO
     * @see com.timetracker.tracker.dto.resp.RecordsForPageDTO
     */
    RecordsForPageDTO getRecordsForPage(GetRecordsForPageDTO req);

}
