package com.timetracker.tracker.dto.resp;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.timetracker.tracker.utils.Constants.*;

/**
 * A data transfer object (DTO) class representing the list of records to be displayed.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordsForPageDTO {

    @NotNull(message = RECORD_LIST_CANNOT_BE_NULL)
    private List<RecordDTO> recordsForPage;

    @NotNull(message = TOTAL_ITEMS_CANNOT_BE_NULL)
    private Long totalItems;

}
