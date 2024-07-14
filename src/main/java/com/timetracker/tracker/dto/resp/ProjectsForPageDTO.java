package com.timetracker.tracker.dto.resp;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.timetracker.tracker.utils.Constants.PROJECT_LIST_CANNOT_BE_NULL;
import static com.timetracker.tracker.utils.Constants.TOTAL_ITEMS_CANNOT_BE_NULL;

/**
 * A data transfer object (DTO) class representing the list of projects to be displayed.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectsForPageDTO {

    @NotNull(message = PROJECT_LIST_CANNOT_BE_NULL)
    private List<ProjectDTO> projectsForPage;

    @NotNull(message = TOTAL_ITEMS_CANNOT_BE_NULL)
    private Long totalItems;

}
