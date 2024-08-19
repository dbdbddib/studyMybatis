package com.study.mustacheapp.member;

import com.study.mustacheapp.ICommonService;
import com.study.mustacheapp.commons.dto.SearchAjaxDto;
import com.study.mustacheapp.security.dto.LoginRequest;
import com.study.mustacheapp.security.dto.SignUpRequest;

import java.util.List;

public interface IMemberService extends ICommonService<MemberDto> {
    IMember login(LoginRequest dto);
    IMember addMember(SignUpRequest dto);
    IMember findByLoginId(String loginId);
    IMember findByNickname(String nickname);
    List<IMember> findAllByLoginIdContains(SearchAjaxDto dto);
    int countAllByLoginIdContains(SearchAjaxDto dto);
}
