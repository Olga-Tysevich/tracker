package com.timetracker.tracker.dto.req;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.timetracker.tracker.utils.serializers.DurationDeserializer;
import com.timetracker.tracker.utils.serializers.DurationSerializer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.Duration;

import static com.timetracker.tracker.utils.Constants.RECORD_ID_CANNOT_BE_NULL;

/**
 * A data transfer object (DTO) class for updating a record.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRecordDTO {

    @NotNull(message = RECORD_ID_CANNOT_BE_NULL)
    private Long id;

    /**
     * The record start date for updating a record. May be {@literal null}.
     */
    @JsonSerialize(using = DateSerializer.class)
    private Date startDate;

    /**
     * The record description for updating a record. May be {@literal null}.
     */
    private String description;

    /**
     * The record duration for updating a record. May be {@literal null}.
     */
    @JsonSerialize(using = DurationSerializer.class)
    @JsonDeserialize(using = DurationDeserializer.class)
    private Duration duration;

}
