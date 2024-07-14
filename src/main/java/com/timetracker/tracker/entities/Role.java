package com.timetracker.tracker.entities;

import com.timetracker.tracker.entities.enums.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

import static com.timetracker.tracker.utils.Constants.*;

/**
 * This class represents a user Role entity with its attributes.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "roles")
@Entity
public class Role implements GrantedAuthority, Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roleIdSeq")
    @SequenceGenerator(name = "roleIdSeq", sequenceName = "role_id_seq", allocationSize = 1)
    @NotNull(message = ROLE_ID_CANNOT_BE_NULL)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "enum(USER_ROLE, ADMIN_ROLE)")
    @NotNull(message = ROLE_CANNOT_BE_NULL)
    private RoleEnum role;

    @Override
    public String getAuthority() {
        return role.name();
    }
}
