package com.study.mustacheapp.boardlike;

import com.study.mustacheapp.commons.inif.IServiceCRUD;

public interface IBoardLikeService extends IServiceCRUD<IBoardLike> {
    Boolean deleteByTableUserBoard(BoardLikeDto dto);
    Integer countByTableUserBoard(IBoardLike searchDto);
}
