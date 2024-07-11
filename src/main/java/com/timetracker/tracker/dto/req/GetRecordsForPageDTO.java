package com.timetracker.tracker.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetRecordsForPageDTO {

    private Integer countPerPage;

    private Integer pageNum;

    private Long userId;

    private Long projectId;

}
