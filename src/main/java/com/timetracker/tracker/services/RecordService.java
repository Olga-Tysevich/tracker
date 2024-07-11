package com.timetracker.tracker.services;

import com.timetracker.tracker.dto.req.CreateRecordDTO;
import com.timetracker.tracker.dto.req.GetRecordsForPageDTO;
import com.timetracker.tracker.dto.req.UpdateRecordDTO;
import com.timetracker.tracker.dto.resp.RecordsForPageDTO;
import org.springframework.stereotype.Service;

@Service
public interface RecordService {

    void createRecord(CreateRecordDTO req);

    void deleteRecord(Long id);

    void updateRecord(UpdateRecordDTO req);

    RecordsForPageDTO getRecordsForPage(GetRecordsForPageDTO req);

}
