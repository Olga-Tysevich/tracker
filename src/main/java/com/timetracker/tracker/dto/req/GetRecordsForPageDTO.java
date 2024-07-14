package com.timetracker.tracker.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

import static com.timetracker.tracker.utils.Constants.PAGE_NUMBER_CANNOT_BE_NULL;
import static com.timetracker.tracker.utils.Constants.PAGE_SIZE_CANNOT_BE_NULL;

/**
 * Data transfer object for retrieving a page of records.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetRecordsForPageDTO {

    /**
     * The number of records to display per page.
     */
    @NotNull(message = PAGE_SIZE_CANNOT_BE_NULL)
    private Integer countPerPage;

    /**
     * The page number to retrieve.First page number 0.
     */
    @NotNull(message = PAGE_NUMBER_CANNOT_BE_NULL)
    private Integer pageNum;

    /**
     * The user ID for filtering records. May be null.
     */
    private Long userId;

    /**
     * The project ID for filtering records. May be null.
     */
    private Long projectId;

    /**
     * The start date for filtering records. May be null.
     */
    private Date startDate;

    /**
     * The end date for filtering records. May be null.
     */
    private Date endDate;

}
