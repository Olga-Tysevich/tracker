package com.timetracker.tracker.dto.req;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.timetracker.tracker.utils.DurationDeserializer;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.Duration;

import static com.timetracker.tracker.utils.Constants.*;

/**
 * A data transfer object (DTO) class for creating a record.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRecordDTO {

    @NotNull(message = USER_ID_CANNOT_BE_NULL)
    @Min(value = MIN_ID, message = MIN_USER_ID_MASSAGE)
    private Long userId;

    @NotNull(message = PROJECT_ID_CANNOT_BE_NULL)
    @Min(value = MIN_ID, message = MIN_PROJECT_ID_MESSAGE)
    private Long projectId;

    @NotBlank(message = DESCRIPTION_CANNOT_BE_EMPTY)
    private String description;

    @NotNull(message = DATE_CANNOT_BE_NULL)
    @DateTimeFormat(pattern = DATE_PATTERN)
    private Date startDate;

    @NotNull(message = DURATION_CANNOT_BE_NULL)
    @JsonDeserialize(using = DurationDeserializer.class)
    private Duration duration;

}
