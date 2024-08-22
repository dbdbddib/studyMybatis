package com.study.mustacheapp.sblike;

public interface ISbLike {
    Long getId();
    void setId(Long id);

    String getTbl();
    void setTbl(String tbl);

    String getNickname();
    void setNickname(String nickname);

    Long getBoardId();
    void setBoardId(Long boardId);

    default void copyFields(ISbLike from) {
        if (from == null) {
            return;
        }
        if (from.getId() != null) {
            this.setId(from.getId());
        }
        if (from.getTbl() != null && !from.getTbl().isEmpty()) {
            this.setTbl(from.getTbl());
        }
        if (from.getNickname() != null && !from.getNickname().isEmpty()) {
            this.setNickname(from.getNickname());
        }
        if (from.getBoardId() != null) {
            this.setBoardId(from.getBoardId());
        }
    }
}
