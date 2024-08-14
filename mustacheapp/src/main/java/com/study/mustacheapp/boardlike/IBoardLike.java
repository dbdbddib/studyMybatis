package com.study.mustacheapp.boardlike;

public interface IBoardLike {
    Long getId();
    void setId(Long id);

    String getTbl();
    void setTbl(String tbl);

    String getLikeUserId();
    void setLikeUserId(String likeUserId);

    Long getBoardId();
    void setBoardId(Long boardId);

    default void copyFields(IBoardLike from) {
        if (from == null) {
            return;
        }
        if (from.getId() != null) {
            this.setId(from.getId());
        }
        if (from.getTbl() != null && !from.getTbl().isEmpty()) {
            this.setTbl(from.getTbl());
        }
        if (from.getLikeUserId() != null && !from.getLikeUserId().isEmpty()) {
            this.setLikeUserId(from.getLikeUserId());
        }
        if (from.getBoardId() != null) {
            this.setBoardId(from.getBoardId());
        }
    }
}
