package com.timetracker.tracker.dto.req;

import com.timetracker.tracker.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserDTO {

    private Long id;
    private String name;
    private String surname;
    private String password;
    private String passwordConfirm;
    private Set<String> roleNames;
}
