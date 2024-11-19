package com.softagape.mustacheajax.member;

import com.softagape.mustacheajax.commons.dto.SearchAjaxDto;
import com.softagape.mustacheajax.commons.inif.IServiceCRUD;
import com.softagape.mustacheajax.security.dto.LoginRequest;

import java.util.List;

public interface IMemberService extends IServiceCRUD<IMember> {
    IMember login(LoginRequest loginRequest);
    Boolean changePassword(IMember dto) throws Exception;
    IMember findByLoginId(String loginId);
    IMember findByNickname(String nickname);
    Integer countAllByNameContains(SearchAjaxDto dto);
    List<IMember> findAllByNameContains(SearchAjaxDto dto);
}
