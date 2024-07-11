package com.timetracker.tracker.controllers;

import com.timetracker.tracker.dto.req.CreateRecordDTO;
import com.timetracker.tracker.dto.req.GetRecordsForPageDTO;
import com.timetracker.tracker.dto.req.UpdateRecordDTO;
import com.timetracker.tracker.dto.resp.RecordsForPageDTO;
import com.timetracker.tracker.services.RecordService;
import com.timetracker.tracker.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tracker/user")
public class UserController {
    private final UserService userService;
    private final RecordService recordService;

    @PostMapping("/create/record")
    public ResponseEntity<?> createRecord(@RequestBody @Valid CreateRecordDTO req) {
        recordService.createRecord(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/update/record")
    public ResponseEntity<?> updateRecord(@RequestBody @Valid UpdateRecordDTO req) {
        recordService.updateRecord(req);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/record")
    public ResponseEntity<?> deleteRecord(@RequestParam
                                          @NotNull(message = "Id cannot be null!")
                                          @Min(value = 1, message = "ID cannot be less than 1")
                                          Long id) {
        recordService.deleteRecord(id);
        return  ResponseEntity.ok().build();
    }

    @GetMapping("/get/record")
    public ResponseEntity<RecordsForPageDTO> getUserRecords(@Valid GetRecordsForPageDTO req) {
        RecordsForPageDTO records = recordService.getRecordsForPage(req);
        return ResponseEntity.ok(records);
    }

}
