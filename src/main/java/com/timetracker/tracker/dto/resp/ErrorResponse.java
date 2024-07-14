package com.timetracker.tracker.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents an ErrorResponse object containing error details.
 * @see com.timetracker.tracker.conf.ForbiddenEntryPoint
 * @see com.timetracker.tracker.conf.TrackerAccessDeniedHandler
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String error;
    private String message;
}
