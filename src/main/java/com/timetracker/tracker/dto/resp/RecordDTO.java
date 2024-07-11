package com.timetracker.tracker.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordDTO {

    private Long id;
    private String userFullName;
    private String projectName;
    private String description;
    private Date startDate;
    private String duration;
    private Date endDate;

}
