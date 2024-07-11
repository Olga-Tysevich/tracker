package com.timetracker.tracker.services.impl;

import com.timetracker.tracker.converters.RecordFilterConverter;
import com.timetracker.tracker.dto.req.CreateRecordDTO;
import com.timetracker.tracker.dto.req.GetRecordsForPageDTO;
import com.timetracker.tracker.dto.req.UpdateRecordDTO;
import com.timetracker.tracker.dto.resp.RecordsForPageDTO;
import com.timetracker.tracker.entities.Record;
import com.timetracker.tracker.exceptions.NotFoundException;
import com.timetracker.tracker.mappers.RecordMapper;
import com.timetracker.tracker.repositories.RecordRepository;
import com.timetracker.tracker.repositories.specifications.RecordFilter;
import com.timetracker.tracker.repositories.specifications.RecordSpecification;
import com.timetracker.tracker.services.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {
    private final RecordRepository recordRepository;

    @Override
    public void createRecord(CreateRecordDTO req) {
        Optional.ofNullable(req)
                .map(RecordMapper.INSTANCE::reqToEntity)
                .map(this::checkRecord)
                .ifPresent(recordRepository::save);
    }

    @Override
    public void deleteRecord(Long id) {
        Optional.ofNullable(id)
                .map(recordRepository::findById)
                .orElseThrow(NotFoundException::new)
                .ifPresent(recordRepository::delete);
    }

    @Override
    public void updateRecord(UpdateRecordDTO req) {
        Record record = Optional.ofNullable(req)
                .flatMap(r -> recordRepository.findById(r.getId()))
                .orElseThrow(NotFoundException::new);
        Record forUpdate = RecordMapper.INSTANCE.reqToEntity(record, req);
        recordRepository.save(forUpdate);
    }

    @Override
    public RecordsForPageDTO getRecordsForPage(GetRecordsForPageDTO req) {
        Page<Record> result = Optional.ofNullable(req)
                .map(r -> PageRequest.of(r.getPageNum(), r.getCountPerPage()))
                .map(pr -> {
                    RecordFilter searchFilter = RecordFilterConverter.convertReqToFilter(req);
                    return recordRepository.findAll(RecordSpecification.search(searchFilter), pr);
                })
                .orElseThrow(NotFoundException::new);
        return RecordMapper.INSTANCE.toRecordList(result.getContent(), result.getTotalElements());
    }

    private Record checkRecord(Record record) {
        Record exist = recordRepository.getByStartDateAndProjectIdAndUserId(record.getStartDate(),
                record.getProject().getId(), record.getUser().getId());
        if (Objects.isNull(exist)) {
            return record;
        }
        exist.setStartDate(record.getStartDate());
        exist.setDuration(record.getDuration());
        return exist;
    }
}
