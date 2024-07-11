package com.timetracker.tracker.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordDTO {

    private Long id;
    private Long userId;
    private Long projectId;
    private LocalDateTime startDate;
    private Integer days;
    private Integer hours;
    private Integer minutes;
}
