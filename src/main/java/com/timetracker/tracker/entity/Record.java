package com.timetracker.tracker.entity;

import com.timetracker.tracker.converters.DurationConverter;
import com.timetracker.tracker.converters.LocalDateTimeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "records")
@Entity
public class Record {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recordIdSeq")
    @SequenceGenerator(name = "recordIdSeq", sequenceName = "record_id_seq", allocationSize = 1)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "project_id")
    private Project project;

    @Column(nullable = false, name = "start_date")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime startDate;

    @Column(nullable = false)
    @Convert(converter = DurationConverter.class)
    private Duration duration;

}
