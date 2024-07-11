package com.timetracker.tracker.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegUserDTO {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String passwordConfirm;
}
