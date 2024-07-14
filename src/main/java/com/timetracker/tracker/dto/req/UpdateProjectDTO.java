package com.timetracker.tracker.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.timetracker.tracker.utils.Constants.PROJECT_ID_CANNOT_BE_NULL;

/**
 * A data transfer object (DTO) class for updating a project.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProjectDTO {

    @NotNull(message = PROJECT_ID_CANNOT_BE_NULL)
    private Long id;

    /**
     * The project name for updating a project. May be {@literal null}.
     */
    private String name;

    /**
     * The project description for updating a project. May be {@literal null}.
     */
    private String description;

}
