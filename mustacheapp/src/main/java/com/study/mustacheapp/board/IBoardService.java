package com.study.mustacheapp.board;

import com.study.mustacheapp.commons.dto.CUDInfoDto;
import com.study.mustacheapp.commons.dto.SearchAjaxDto;
import com.study.mustacheapp.commons.inif.IServiceCRUD;

import java.util.List;

public interface IBoardService extends IServiceCRUD<BoardDto> {
    void addViewQty(Long id);
    void addLikeQty(CUDInfoDto cudInfoDto, Long id);
    void subLikeQty(CUDInfoDto cudInfoDto, Long id);

    Integer countAllByNameContains(SearchAjaxDto searchAjaxDto);
    List<BoardDto> findAllByNameContains(SearchAjaxDto searchAjaxDto);
}
