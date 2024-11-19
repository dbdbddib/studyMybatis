package com.softagape.mustacheajax.commentlike;

public interface ICommentLike {
    Long getId();
    void setId(Long id);

    String getCommentTbl();
    void setCommentTbl(String commentTbl);

    Long getCreateId();
    void setCreateId(Long createId);

    Long getCommentId();
    void setCommentId(Long commentId);

    default void copyFields(ICommentLike from) {
        if (from == null) {
            return;
        }
        if (from.getId() != null) {
            this.setId(from.getId());
        }
        if (from.getCommentTbl() != null && !from.getCommentTbl().isEmpty()) {
            this.setCommentTbl(from.getCommentTbl());
        }
        if (from.getCreateId() != null) {
            this.setCreateId(from.getCreateId());
        }
        if (from.getCommentId() != null) {
            this.setCommentId(from.getCommentId());
        }
    }
}
