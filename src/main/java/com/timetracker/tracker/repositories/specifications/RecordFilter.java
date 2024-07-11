package com.timetracker.tracker.repositories.specifications;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordFilter {

    private Long userId;

    private Long projectId;

    private Date startDate;

    private Date endDate;

}
