package com.rc.runlog.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserRequest {
    private String username;
    private String email;
    private String password;
}
