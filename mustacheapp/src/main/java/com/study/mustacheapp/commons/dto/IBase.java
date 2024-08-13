package com.study.mustacheapp.commons.dto;

public interface IBase {
    String getCreateDt();
    void setCreateDt(String createDt);

    String getCreateId();
    void setCreateId(String createId);

    String getUpdateDt();
    void setUpdateDt(String updateDt);

    String getUpdateId();
    void setUpdateId(String updateId);

    String getDeleteDt();
    void setDeleteDt(String deleteDt);

    String getDeleteId();
    void setDeleteId(String deleteId);

    default void copyFields(IBase from) {
        if (from == null) {
            return;
        }
        if (from.getCreateDt() != null && !from.getCreateDt().isEmpty()) {
            this.setCreateDt(from.getCreateDt());
        }
        if (from.getCreateId() != null && !from.getCreateId().isEmpty()) {
            this.setCreateId(from.getCreateId());
        }
        if (from.getUpdateDt() != null && !from.getUpdateDt().isEmpty()) {
            this.setUpdateDt(from.getUpdateDt());
        }
        if (from.getUpdateId() != null && !from.getUpdateId().isEmpty()) {
            this.setUpdateId(from.getUpdateId());
        }
        if (from.getDeleteDt() != null && !from.getDeleteDt().isEmpty()) {
            this.setDeleteDt(from.getDeleteDt());
        }
        if (from.getDeleteId() != null && !from.getDeleteId().isEmpty()) {
            this.setDeleteId(from.getDeleteId());
        }
    }
}
