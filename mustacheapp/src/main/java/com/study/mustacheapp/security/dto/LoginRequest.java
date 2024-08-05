package com.study.mustacheapp.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class LoginRequest {
    private String loginId;
    private String password;
}
