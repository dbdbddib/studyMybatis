package com.study.mustacheapp.member;

import com.study.mustacheapp.commons.dto.BaseDto;
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
public class MemberDto extends BaseDto implements IMember {
    private Long id;
    private String name;
    private Long userId;
    private String loginId;
    private String password;
    private String email;
    private String role;
    private Boolean active;
}
