package com.timetracker.tracker.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static com.timetracker.tracker.utils.Constants.*;

/**
 * This class represents a Project entity with its attributes.
 */
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
    @NotNull(message = PROJECT_ID_CANNOT_BE_NULL)
    private Long id;

    @Column
    @NotBlank(message = NAME_CANNOT_BE_EMPTY)
    private String name;

    @Column
    @NotBlank(message = DESCRIPTION_CANNOT_BE_EMPTY)
    private String description;

}
