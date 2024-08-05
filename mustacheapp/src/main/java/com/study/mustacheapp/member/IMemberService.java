package com.study.mustacheapp.member;

import com.study.mustacheapp.ICommonService;
import com.study.mustacheapp.SearchAjaxDto;

import java.util.List;

public interface IMemberService extends ICommonService<MemberDto> {
    IMember addMember(SignUpRequest dto);
    IMember findByLoginId(String loginId);
    List<IMember> findAllByLoginIdContains(SearchAjaxDto dto);
    int countAllByLoginIdContains(SearchAjaxDto searchMemberDto);
}
