package com.timetracker.tracker.entities;

import com.timetracker.tracker.entities.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

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
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "enum(USER_ROLE, ADMIN_ROLE)")
    private RoleEnum role;

    @Override
    public String getAuthority() {
        return role.name();
    }
}
