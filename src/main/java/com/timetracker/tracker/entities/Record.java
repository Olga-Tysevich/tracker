package com.timetracker.tracker.entities;

import com.timetracker.tracker.converters.DurationConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.Duration;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "project_id")
    private Project project;

    @Column
    private String description;

    @Column(nullable = false, name = "start_date")
    private Date startDate;

    @Column(nullable = false)
    @Convert(converter = DurationConverter.class)
    private Duration duration;

    @Column(nullable = false, name = "end_date")
    private Date endDate;

}
