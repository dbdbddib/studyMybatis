package com.softagape.mustacheajax.board.comment;

import com.softagape.mustacheajax.commons.dto.IBase;

public interface IBoardComment extends IBase {
    Long getId();
    void setId(Long id);

    String getComment();
    void setComment(String comment);

    Integer getLikeQty();
    void setLikeQty(Integer likeQty);

    Long getBoardId();
    void setBoardId(Long boardId);

    String getTbl();

    default void copyFields(IBoardComment from) {
        if (from == null) {
            return;
        }
        if (from.getId() != null) {
            this.setId(from.getId());
        }
        if (from.getComment() != null && !from.getComment().isEmpty()) {
            this.setComment(from.getComment());
        }
        if (from.getLikeQty() != null) {
            this.setLikeQty(from.getLikeQty());
        }
        if (from.getBoardId() != null) {
            this.setBoardId(from.getBoardId());
        }
        IBase.super.copyFields(from);
    }
}
