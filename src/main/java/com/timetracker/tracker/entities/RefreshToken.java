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
 * This class represents a RefreshToken entity with its attributes.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "refresh_tokens")
@Entity
public class RefreshToken implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recordIdSeq")
    @SequenceGenerator(name = "recordIdSeq", sequenceName = "record_id_seq", allocationSize = 1)
    @NotNull(message = TOKEN_ID_CANNOT_BE_NULL)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    @NotBlank(message = USER_CANNOT_BE_NULL)
    private User user;

    @Column(name = "refresh_token", unique = true, nullable = false)
    @NotBlank(message = REFRESH_TOKEN_CANNOT_BE_NULL_OR_EMPTY)
    private String tokenValue;

}
