package com.timetracker.tracker.entities;

import com.timetracker.tracker.converters.DurationConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.time.Duration;

import static com.timetracker.tracker.utils.Constants.*;

/**
 * This class represents a Record entity with its attributes.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "records")
@Entity
public class Record implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recordIdSeq")
    @SequenceGenerator(name = "recordIdSeq", sequenceName = "record_id_seq", allocationSize = 1)
    @NotNull(message = RECORD_ID_CANNOT_BE_NULL)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    @NotNull(message = USER_CANNOT_BE_NULL)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "project_id")
    @NotNull(message = PROJECT_CANNOT_BE_NULL)
    private Project project;

    @Column
    @NotBlank(message = DESCRIPTION_CANNOT_BE_EMPTY)
    private String description;

    @Column(nullable = false, name = "start_date")
    @NotNull(message = START_DATE_CANNOT_BE_NULL)
    private Date startDate;

    @Column(nullable = false)
    @Convert(converter = DurationConverter.class)
    @NotNull(message = DURATION_CANNOT_BE_NULL)
    private Duration duration;

    @Column(nullable = false, name = "end_date")
    @NotNull(message = END_DATE_CANNOT_BE_NULL)
    private Date endDate;

}
