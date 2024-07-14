package com.timetracker.tracker.services.impl;

import com.timetracker.tracker.converters.RecordFilterConverter;
import com.timetracker.tracker.dto.req.CreateRecordDTO;
import com.timetracker.tracker.dto.req.GetRecordsForPageDTO;
import com.timetracker.tracker.dto.req.UpdateRecordDTO;
import com.timetracker.tracker.dto.resp.RecordsForPageDTO;
import com.timetracker.tracker.entities.Project;
import com.timetracker.tracker.entities.Record;
import com.timetracker.tracker.entities.User;
import com.timetracker.tracker.exceptions.NotFoundException;
import com.timetracker.tracker.exceptions.UserNotFoundException;
import com.timetracker.tracker.mappers.RecordMapper;
import com.timetracker.tracker.repositories.ProjectRepository;
import com.timetracker.tracker.repositories.RecordRepository;
import com.timetracker.tracker.repositories.UserRepository;
import com.timetracker.tracker.repositories.specifications.RecordFilter;
import com.timetracker.tracker.repositories.specifications.RecordSpecification;
import com.timetracker.tracker.services.RecordService;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static com.timetracker.tracker.utils.Constants.PROJECT_NOT_FOUND;
import static com.timetracker.tracker.utils.Constants.REQ_CANNOT_BE_NULL;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {
    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Override
    public void createRecord(CreateRecordDTO req) {
        if (Objects.isNull(req)) {
            throw new IllegalArgumentException(REQ_CANNOT_BE_NULL);
        }
        User user = userRepository.findById(req.getUserId()).orElseThrow(UserNotFoundException::new);
        Project project = projectRepository.findById(req.getProjectId()).orElseThrow(() -> new NotFoundException(PROJECT_NOT_FOUND));
        Record record = RecordMapper.INSTANCE.toEntity(user, project, req);
        checkRecord(record);
        recordRepository.save(record);
    }

    @Override
    public void deleteRecord(Long id) {
        Optional.ofNullable(id).ifPresent(recordRepository::deleteById);
    }

    @Override
    public void updateRecord(UpdateRecordDTO req) {
        if (Objects.isNull(req)) {
            throw new IllegalArgumentException(REQ_CANNOT_BE_NULL);
        }
        Record record = recordRepository.findById(req.getId())
                .orElseThrow(NotFoundException::new);
        Record forUpdate = RecordMapper.INSTANCE.mergeReqAndEntity(record, req);
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
