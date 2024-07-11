package com.timetracker.tracker.converters;

import com.timetracker.tracker.dto.req.GetRecordsForPageDTO;
import com.timetracker.tracker.repositories.specifications.RecordFilter;
import jakarta.validation.constraints.NotNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RecordFilterConverter {

    public RecordFilter convertReqToFilter(@NotNull GetRecordsForPageDTO req) {
        return RecordFilter.builder()
                .userId(req.getUserId())
                .projectId(req.getProjectId())
                .startDate(req.getStartDate())
                .endDate(req.getEndDate())
                .build();
    }

}
