package com.timetracker.tracker.entities;

import com.timetracker.tracker.entities.enums.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.timetracker.tracker.utils.Constants.*;

/**
 * This class represents a User entity with its attributes.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
@Entity
public class User implements UserDetails, Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userIdSeq")
    @SequenceGenerator(name = "userIdSeq", sequenceName = "user_id_seq", allocationSize = 1)
    @NotNull(message = USER_ID_CANNOT_BE_NULL)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = NAME_CANNOT_BE_EMPTY)
    private String name;

    @Column(nullable = false)
    @NotBlank(message = SURNAME_CANNOT_BE_NULL_OR_EMPTY)
    private String surname;

    @Column(nullable = false, unique = true)
    @NotBlank(message = EMAIL_CANNOT_BE_NULL_OR_EMPTY)
    @Pattern(regexp = REGEXP_EMAIL, message = INVALID_EMAIL_MESSAGE)
    private String email;

    @Column(nullable = false)
    @NotBlank(message = PASSWORD_CANNOT_BE_NULL_OR_EMPTY)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @NotEmpty(message = THE_ROLE_SET_CANNOT_BE_EMPTY)
    private Set<Role> roleSet = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleSet;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean hasRoleAdmin() {
        return roleSet.stream()
                .anyMatch(role -> role.getRole() == RoleEnum.ROLE_ADMIN);
    }

}
