package com.study.mustacheapp.board;

import com.study.mustacheapp.commons.dto.SearchAjaxDto;
import com.study.mustacheapp.commons.inif.IMybatisCRUD;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IBoardMybatisMapper extends IMybatisCRUD<BoardDto> {
    void addViewQty(Long id);
    void addLikeQty(Long id);
    void subLikeQty(Long id);

    Integer countAllByNameContains(SearchAjaxDto searchAjaxDto);
    List<BoardDto> findAllByNameContains(SearchAjaxDto searchAjaxDto);
}
