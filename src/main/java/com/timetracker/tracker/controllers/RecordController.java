package com.timetracker.tracker.controllers;

import com.timetracker.tracker.dto.req.CreateRecordDTO;
import com.timetracker.tracker.dto.req.GetRecordsForPageDTO;
import com.timetracker.tracker.dto.req.UpdateRecordDTO;
import com.timetracker.tracker.dto.resp.RecordsForPageDTO;
import com.timetracker.tracker.entities.User;
import com.timetracker.tracker.facades.RecordFacade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling Record objects.
 *
 * @see com.timetracker.tracker.entities.Record
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tracker/record")
public class RecordController {
    /**
     * RecordFacade interface
     *
     * @see com.timetracker.tracker.facades.RecordFacade
     */
    private final RecordFacade recordFacade;

    /**
     * Endpoint to create a new record.
     *
     * @param req the request containing record details.
     * @return ResponseEntity with success status.
     * @see com.timetracker.tracker.dto.req.CreateRecordDTO
     */
    @PostMapping("/create")
    public ResponseEntity<?> createRecord(@RequestBody @Valid CreateRecordDTO req) {
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        req.setUserId(userId);
        recordFacade.createRecord(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Endpoint to update an existing record.
     *
     * @param req the request containing updated record details
     * @return ResponseEntity with success status.
     * @see com.timetracker.tracker.dto.req.UpdateRecordDTO
     */
    @PostMapping("/update")
    public ResponseEntity<?> updateRecord(@RequestBody @Valid UpdateRecordDTO req) {
        recordFacade.updateRecord(req);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint to delete a record by ID.
     *
     * @param id the ID of the record to be deleted.
     * @return ResponseEntity with success status.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteRecord(@RequestParam
                                          @NotNull(message = "Id cannot be null!")
                                          @Min(value = 1, message = "ID cannot be less than 1")
                                          Long id) {
        recordFacade.deleteRecord(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint to get a list of records for a specific page.
     *
     * @param req the request containing parameters for pagination.
     * @return ResponseEntity with RecordsForPageDTO object.
     * @see com.timetracker.tracker.dto.req.GetRecordsForPageDTO
     */
    @GetMapping("/admin/get")
    public ResponseEntity<RecordsForPageDTO> getRecords(@Valid GetRecordsForPageDTO req) {
        RecordsForPageDTO records = recordFacade.getRecordsForPage(req);
        return ResponseEntity.ok(records);
    }

    /**
     * Endpoint for getting a list of a specific user's records for a specific page.
     *
     * @param req the request containing parameters for pagination and userId.
     * @return ResponseEntity with RecordsForPageDTO object.
     * @see com.timetracker.tracker.dto.req.GetRecordsForPageDTO
     */
    @GetMapping("/get")
    public ResponseEntity<RecordsForPageDTO> getUserRecords(@Valid GetRecordsForPageDTO req) {
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        req.setUserId(userId);
        RecordsForPageDTO records = recordFacade.getRecordsForPage(req);
        return ResponseEntity.ok(records);
    }

}
