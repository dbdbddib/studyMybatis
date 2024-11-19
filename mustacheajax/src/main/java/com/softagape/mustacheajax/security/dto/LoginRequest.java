package com.softagape.mustacheajax.security.dto;

import com.softagape.mustacheajax.commons.dto.BaseNullRequest;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest extends BaseNullRequest {
    @Size(min = 8, max = 10, message = "로그인ID는 8~10자 이어야 합니다.")
    private String loginId;
    @Size(min = 8, max = 20, message = "암호는 8~20자 이어야 합니다.")
    private String password;
}
