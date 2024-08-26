package com.study.mustacheapp.commons.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface IBase {
    String getCreateDt();
    void setCreateDt(String createDt);

    Long getCreateId();
    void setCreateId(Long createId);

    String getUpdateDt();
    void setUpdateDt(String updateDt);

    Long getUpdateId();
    void setUpdateId(Long updateId);

    String getDeleteDt();
    void setDeleteDt(String deleteDt);

    Long getDeleteId();
    void setDeleteId(Long deleteId);

    Boolean getDeleteFlag();
    void setDeleteFlag(Boolean deleteFlag);

    default void copyFields(IBase from) {
        if (from == null) {
            return;
        }
        if (from.getCreateDt() != null && !from.getCreateDt().isEmpty()) {
            this.setCreateDt(from.getCreateDt());
        }
        if (from.getCreateId() != null) {
            this.setCreateId(from.getCreateId());
        }
        if (from.getUpdateDt() != null && !from.getUpdateDt().isEmpty()) {
            this.setUpdateDt(from.getUpdateDt());
        }
        if (from.getUpdateId() != null) {
            this.setUpdateId(from.getUpdateId());
        }
        if (from.getDeleteDt() != null && !from.getDeleteDt().isEmpty()) {
            this.setDeleteDt(from.getDeleteDt());
        }
        if (from.getDeleteId() != null) {
            this.setDeleteId(from.getDeleteId());
        }
        if (from.getDeleteFlag() != null) {
            this.setDeleteFlag(from.getDeleteFlag());
        }
    }

    default String getSystemDt() {
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(today);
    }

    default void setCreateInfo(Long nickname) {
        this.setCreateDt(this.getSystemDt());
        this.setCreateId(nickname);
    }

    default void setUpdateInfo(Long nickname) {
        this.setUpdateDt(this.getSystemDt());
        this.setUpdateId(nickname);
    }

    default void setDeleteInfo(Long nickname) {
        this.setDeleteDt(this.getSystemDt());
        this.setDeleteId(nickname);
    }
}
