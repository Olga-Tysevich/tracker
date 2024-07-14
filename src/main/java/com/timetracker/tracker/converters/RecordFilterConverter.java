package com.timetracker.tracker.converters;

import com.timetracker.tracker.dto.req.GetRecordsForPageDTO;
import com.timetracker.tracker.repositories.specifications.RecordFilter;
import jakarta.validation.constraints.NotNull;
import lombok.experimental.UtilityClass;

/**
 * Utility class for converting a request to a RecordFilter object.
 */
@UtilityClass
public class RecordFilterConverter {
    /**
     * Converts a GetRecordsForPageDTO request to a RecordFilter object.
     *
     * @param req the request to convert.
     * @return the converted RecordFilter object.
     * @see com.timetracker.tracker.dto.req.GetRecordsForPageDTO
     */
    public RecordFilter convertReqToFilter(@NotNull GetRecordsForPageDTO req) {
        return RecordFilter.builder()
                .userId(req.getUserId())
                .projectId(req.getProjectId())
                .startDate(req.getStartDate())
                .endDate(req.getEndDate())
                .build();
    }

}
