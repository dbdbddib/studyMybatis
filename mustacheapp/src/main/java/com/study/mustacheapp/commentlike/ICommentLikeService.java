package com.study.mustacheapp.commentlike;

import com.study.mustacheapp.commons.inif.IServiceCRUD;

public interface ICommentLikeService extends IServiceCRUD<ICommentLike> {
    Boolean deleteByCommentTableUserBoard(CommentLikeDto dto);
    Integer countByCommentTableUserBoard(ICommentLike searchDto);
}
