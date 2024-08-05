package com.study.mustacheapp.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberDto implements IMember {
    private Long id;
    private String name;
    private String loginId;
    private String password;
    private String email;
    private String role;
}
