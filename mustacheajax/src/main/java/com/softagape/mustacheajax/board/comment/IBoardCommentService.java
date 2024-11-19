package com.softagape.mustacheajax.board.comment;

import com.softagape.mustacheajax.commons.dto.CUDInfoDto;
import com.softagape.mustacheajax.commons.dto.SearchAjaxDto;
import com.softagape.mustacheajax.commons.inif.IServiceCRUD;
import com.softagape.mustacheajax.member.IMember;

import java.util.List;

public interface IBoardCommentService extends IServiceCRUD<BoardCommentDto> {
    void addLikeQty(CUDInfoDto cudInfoDto, Long id);
    void subLikeQty(CUDInfoDto cudInfoDto, Long id);

    Integer countAllByBoardId(SearchAjaxDto search);
    List<BoardCommentDto> findAllByBoardId(IMember loginUser, SearchBoardCommentDto dto);
}
