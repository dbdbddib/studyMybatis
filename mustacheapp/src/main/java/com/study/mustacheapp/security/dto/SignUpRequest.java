package com.study.mustacheapp.security.dto;

import com.study.mustacheapp.commons.dto.BaseNullRequest;
import com.study.mustacheapp.member.IMember;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest extends BaseNullRequest implements IMember {
    private Long id;
    private String name;
    private String nickname;
    private String loginId;
    private String password;
    private String email;
    private String role;
    private Boolean Active;
}
