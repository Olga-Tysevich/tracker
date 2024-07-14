package com.timetracker.tracker.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "projects")
@Entity
public class Project implements Serializable {
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
