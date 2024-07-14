package com.timetracker.tracker.dto.resp;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.timetracker.tracker.utils.Constants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionResponse {

    @NotNull(message = HTTP_STATUS_CODE_CANNOT_BE_NULL)
    private Integer HttpStatusCode;

    @NotBlank(message = EXCEPTION_MESSAGE_CANNOT_BE_NULL)
    private String ExceptionMessage;

    @NotEmpty(message = EXCEPTION_DETAILS_CANNOT_BE_EMPTY)
    private List<String> ExceptionDetails;

    @NotNull(message = TIMESTAMP_CANNOT_BE_NULL)
    private LocalDateTime TimeStamp;

}