package com.study.mustacheapp.board.comment;

import com.study.mustacheapp.commons.dto.CUDInfoDto;
import com.study.mustacheapp.commons.dto.SearchAjaxDto;
import com.study.mustacheapp.commons.inif.IServiceCRUD;
import com.study.mustacheapp.member.IMember;

import java.util.List;

public interface IBoardCommentService extends IServiceCRUD<BoardCommentDto> {
    void addLikeQty(CUDInfoDto cudInfoDto, Long id);
    void subLikeQty(CUDInfoDto cudInfoDto, Long id);

    Integer countAllByBoardId(SearchAjaxDto search);
    List<BoardCommentDto> findAllByBoardId(IMember loginUser, SearchBoardCommentDto dto);
}
