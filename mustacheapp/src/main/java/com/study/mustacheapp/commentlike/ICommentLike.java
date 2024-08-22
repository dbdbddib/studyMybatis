package com.study.mustacheapp.commentlike;

public interface ICommentLike {
    Long getId();
    void setId(Long id);

    String getCommentTbl();
    void setCommentTbl(String commentTbl);

    String getNickname();
    void setNickname(String nickname);

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
        if (from.getNickname() != null && !from.getNickname().isEmpty()) {
            this.setNickname(from.getNickname());
        }
        if (from.getCommentId() != null) {
            this.setCommentId(from.getCommentId());
        }
    }
}
