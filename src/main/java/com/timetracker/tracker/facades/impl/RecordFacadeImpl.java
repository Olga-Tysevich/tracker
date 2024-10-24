package com.timetracker.tracker.facades.impl;

import com.timetracker.tracker.controllers.converters.RecordFilterConverter;
import com.timetracker.tracker.dto.req.CreateRecordDTO;
import com.timetracker.tracker.dto.req.GetRecordsForPageDTO;
import com.timetracker.tracker.dto.req.UpdateRecordDTO;
import com.timetracker.tracker.dto.resp.RecordsForPageDTO;
import com.timetracker.tracker.entities.Project;
import com.timetracker.tracker.entities.Record;
import com.timetracker.tracker.entities.User;
import com.timetracker.tracker.exceptions.NotFoundException;
import com.timetracker.tracker.exceptions.UserNotFoundException;
import com.timetracker.tracker.facades.RecordFacade;
import com.timetracker.tracker.mappers.RecordMapper;
import com.timetracker.tracker.repositories.specifications.RecordFilter;
import com.timetracker.tracker.services.ProjectService;
import com.timetracker.tracker.services.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

import static com.timetracker.tracker.utils.Constants.PROJECT_NOT_FOUND;
import static com.timetracker.tracker.utils.Constants.REQ_CANNOT_BE_NULL;

/**
 * This class implements the RecordFacade interface and contains methods for creating, deleting, updating, and retrieving records.
 *
 * @see com.timetracker.tracker.facades.RecordFacade
 */

@RequiredArgsConstructor
@Component
public class RecordFacadeImpl implements RecordFacade {
    /**
     * RecordService bean.
     *
     * @see com.timetracker.tracker.services.RecordService
     */
    private final RecordService recordService;
    /**
     * ProjectService bean.
     *
     * @see com.timetracker.tracker.services.ProjectService
     */
    private final ProjectService projectService;

    /**
     * Creates a new record.
     *
     * @param req the create record request.
     * @throws IllegalArgumentException if the request is {@literal null}.
     * @throws UserNotFoundException if the related user is not found.
     * @throws NotFoundException if the related project is not found.
     * @see com.timetracker.tracker.dto.req.CreateRecordDTO
     */
    @Override
    public void createRecord(CreateRecordDTO req) {
        if (Objects.isNull(req)) {
            throw new IllegalArgumentException(REQ_CANNOT_BE_NULL);
        }
        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Project project = projectService.getProjectById(req.getProjectId()).orElseThrow(() -> new NotFoundException(PROJECT_NOT_FOUND));
        Record record = RecordMapper.INSTANCE.toEntity(user, project, req);
        recordService.createRecord(record);
    }

    /**
     * Deletes a record by its ID.
     *
     * @param id the ID of the record to delete.
     */
    @Override
    public void deleteRecord(Long id) {
        Optional.ofNullable(id).ifPresent(recordService::deleteRecord);
    }

    /**
     * Updates an existing record.
     *
     * @param req the update record request.
     * @throws IllegalArgumentException if the request is {@literal null}.
     * @throws NotFoundException if the record for update is not found.
     * @see com.timetracker.tracker.dto.req.UpdateRecordDTO
     */
    @Override
    public void updateRecord(UpdateRecordDTO req) {
        if (Objects.isNull(req)) {
            throw new IllegalArgumentException(REQ_CANNOT_BE_NULL);
        }
        Record record = recordService.getRecordById(req.getId())
                .orElseThrow(NotFoundException::new);
        Record forUpdate = RecordMapper.INSTANCE.mergeReqAndEntity(record, req);
        recordService.updateRecord(forUpdate);
    }

    /**
     * Retrieves a list of records for a specific page for User.
     *
     * @param req the request for getting records for a page.
     * @return the list of records for the specified page.
     * @throws NotFoundException if the request is {@literal null}.
     * @see com.timetracker.tracker.dto.req.GetRecordsForPageDTO
     */
    @Override
    public RecordsForPageDTO getUserRecordsForPage(GetRecordsForPageDTO req) {
        long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        req.setUserId(userId);
        return getRecordsForPageByFilter(req);
    }

    /**
     * Retrieves a list of records for a specific page for Admin.
     *
     * @param req the request for getting records for a page.
     * @return the list of records for the specified page.
     * @throws NotFoundException if the request is {@literal null}.
     * @see com.timetracker.tracker.dto.req.GetRecordsForPageDTO
     */
    @Override
    public RecordsForPageDTO getRecordsForPage(GetRecordsForPageDTO req) {
        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (user.hasRoleAdmin()) {
            return getRecordsForPageByFilter(req);
        } else {
            return getUserRecordsForPage(req);
        }
    }

    /**
     * Retrieves a list of records for a specific page.
     *
     * @param req the request for getting records for a page.
     * @return the list of records for the specified page.
     * @throws NotFoundException if the request is {@literal null}.
     * @see com.timetracker.tracker.dto.req.GetRecordsForPageDTO
     */
    private RecordsForPageDTO getRecordsForPageByFilter(GetRecordsForPageDTO req) {

        Page<Record> result = Optional.ofNullable(req)
                .map(r -> {
                    PageRequest request = PageRequest.of(r.getPageNum(), r.getCountPerPage());
                    RecordFilter searchFilter = RecordFilterConverter.convertReqToFilter(r);
                    return recordService.getRecordsForPage(request, searchFilter);
                })
                .orElseThrow(NotFoundException::new);
        return RecordMapper.INSTANCE.toRecordList(result.getContent(), result.getTotalElements());
    }


}
