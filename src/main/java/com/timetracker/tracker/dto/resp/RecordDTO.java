package com.timetracker.tracker.dto.resp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

import static com.timetracker.tracker.utils.Constants.*;

/**
 * A data transfer object (DTO) class representing the record to be displayed.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordDTO {

    @NotNull(message = RECORD_ID_CANNOT_BE_NULL)
    private Long id;

    @NotBlank(message = USER_FULL_NAME_CANNOT_BE_NULL)
    private String userFullName;

    @NotBlank(message = PROJECT_NAME_CANNOT_BE_NULL)
    private String projectName;

    @NotBlank(message = PROJECT_DESCRIPTION_CANNOT_BE_NULL)
    private String description;

    @NotNull(message = START_DATE_CANNOT_BE_NULL)
    private Date startDate;

    @NotNull(message = DURATION_CANNOT_BE_NULL)
    private String duration;

    @NotNull(message = END_DATE_CANNOT_BE_NULL)
    private Date endDate;

}
