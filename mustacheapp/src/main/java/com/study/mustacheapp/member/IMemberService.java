package com.study.mustacheapp.member;

import com.study.mustacheapp.commons.dto.SearchAjaxDto;
import com.study.mustacheapp.commons.inif.IServiceCRUD;
import com.study.mustacheapp.security.dto.LoginRequest;

import java.util.List;

public interface IMemberService extends IServiceCRUD<IMember> {
    IMember login(LoginRequest loginRequest);
    Boolean changePassword(IMember dto) throws Exception;
    IMember findByLoginId(String loginId);
    IMember findByNickname(String nickname);
    Integer countAllByNameContains(SearchAjaxDto dto);
    List<IMember> findAllByNameContains(SearchAjaxDto dto);
}
