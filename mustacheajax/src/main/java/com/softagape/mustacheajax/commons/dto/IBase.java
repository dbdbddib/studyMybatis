package com.softagape.mustacheajax.commons.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface IBase {
    String getCreateDt();
    void setCreateDt(String createDt);

    Long getCreateId();
    void setCreateId(Long createId);

    String getCreateName();
    void setCreateName(String createName);

    String getUpdateDt();
    void setUpdateDt(String updateDt);

    Long getUpdateId();
    void setUpdateId(Long updateId);

    String getUpdateName();
    void setUpdateName(String updateName);

    String getDeleteDt();
    void setDeleteDt(String deleteDt);

    Long getDeleteId();
    void setDeleteId(Long deleteId);

    String getDeleteName();
    void setDeleteName(String deleteName);

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
        if (from.getCreateName() != null && !from.getCreateName().isEmpty()) {
            this.setCreateName(from.getCreateName());
        }
        if (from.getUpdateDt() != null && !from.getUpdateDt().isEmpty()) {
            this.setUpdateDt(from.getUpdateDt());
        }
        if (from.getUpdateId() != null) {
            this.setUpdateId(from.getUpdateId());
        }
        if (from.getUpdateName() != null && !from.getUpdateName().isEmpty()) {
            this.setUpdateName(from.getUpdateName());
        }
        if (from.getDeleteDt() != null && !from.getDeleteDt().isEmpty()) {
            this.setDeleteDt(from.getDeleteDt());
        }
        if (from.getDeleteId() != null) {
            this.setDeleteId(from.getDeleteId());
        }
        if (from.getDeleteName() != null && !from.getDeleteName().isEmpty()) {
            this.setDeleteName(from.getDeleteName());
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

    default void setCreateInfo(Long memberId) {
        this.setCreateDt(this.getSystemDt());
        this.setCreateId(memberId);
    }

    default void setUpdateInfo(Long memberId) {
        this.setUpdateDt(this.getSystemDt());
        this.setUpdateId(memberId);
    }

    default void setDeleteInfo(Long memberId) {
        this.setDeleteDt(this.getSystemDt());
        this.setDeleteId(memberId);
    }
}
