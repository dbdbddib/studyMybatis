package com.study.mustacheapp.member;

import com.study.mustacheapp.commons.dto.SearchAjaxDto;
import com.study.mustacheapp.commons.inif.IMybatisCRUD;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IMemberMybatisMapper extends IMybatisCRUD<MemberDto> {
    MemberDto findByLoginId(String loginId);
    MemberDto findByNickname(String nickname);
    void changePassword(MemberDto dto);

    Integer countAllByNameContains(SearchAjaxDto search);
    List<MemberDto> findAllByNameContains(SearchAjaxDto search);
}