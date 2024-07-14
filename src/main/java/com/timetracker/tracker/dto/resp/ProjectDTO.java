package com.timetracker.tracker.dto.resp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.timetracker.tracker.utils.Constants.*;

/**
 * A data transfer object (DTO) class representing the project to be displayed.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDTO {

    @NotNull(message = PROJECT_ID_CANNOT_BE_NULL)
    private Long id;

    @NotBlank(message = NAME_CANNOT_BE_EMPTY)
    private String name;

    @NotBlank(message = DESCRIPTION_CANNOT_BE_EMPTY)
    private String description;

}
