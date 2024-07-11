package com.timetracker.tracker.dto.req;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.timetracker.tracker.utils.DurationDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRecordDTO {

    private Long id;

    private Long userId;

    private Long projectId;

    private String description;

    private Date startDate;

    @JsonDeserialize(using = DurationDeserializer.class)
    private Duration duration;

}
