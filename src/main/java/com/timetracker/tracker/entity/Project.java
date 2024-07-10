package com.timetracker.tracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "projects")
@Entity
public class Project {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "projectIdSeq")
    @SequenceGenerator(name = "projectIdSeq", sequenceName = "project_id_seq", allocationSize = 1)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

}
