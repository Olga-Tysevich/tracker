package com.timetracker.tracker.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.timetracker.tracker.utils.Constants.PAGE_NUMBER_CANNOT_BE_NULL;
import static com.timetracker.tracker.utils.Constants.PAGE_SIZE_CANNOT_BE_NULL;

/**
 * Data transfer object for retrieving a page of projects.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetProjectsForPageDTO {

    /**
     * The number of projects to display per page.
     */
    @NotNull(message = PAGE_SIZE_CANNOT_BE_NULL)
    private Integer countPerPage;

    /**
     * The page number to retrieve.First page number 0.
     */
    @NotNull(message = PAGE_NUMBER_CANNOT_BE_NULL)
    private Integer pageNum;

}
