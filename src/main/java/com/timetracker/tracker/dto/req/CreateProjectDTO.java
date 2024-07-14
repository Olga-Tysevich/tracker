package com.timetracker.tracker.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.timetracker.tracker.utils.Constants.DESCRIPTION_CANNOT_BE_EMPTY;
import static com.timetracker.tracker.utils.Constants.NAME_CANNOT_BE_EMPTY;

/**
 * A data transfer object (DTO) class for creating a project.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProjectDTO {

    @NotBlank(message = NAME_CANNOT_BE_EMPTY)
    private String name;

    @NotBlank(message = DESCRIPTION_CANNOT_BE_EMPTY)
    private String description;

}
