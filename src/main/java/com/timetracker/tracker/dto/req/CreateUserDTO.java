package com.timetracker.tracker.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDTO {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String passwordConfirm;
    private Set<String> roleNames;
}
