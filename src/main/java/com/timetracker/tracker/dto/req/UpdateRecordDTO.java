package com.timetracker.tracker.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRecordDTO {

    private Long id;

    private LocalDateTime startDate;

    private String duration;

}
