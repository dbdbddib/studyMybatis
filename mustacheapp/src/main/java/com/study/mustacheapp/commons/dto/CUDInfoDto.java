package com.study.mustacheapp.commons.dto;

import com.study.mustacheapp.member.IMember;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class CUDInfoDto {
    private final IMember loginUser;

    public CUDInfoDto(IMember loginUser) {
        this.loginUser = loginUser;
    }

    private String getSystemDt() {
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(today);
    }

    public void setCreateInfo(IBase iBase) {
        if (iBase == null) {
            return;
        }
        iBase.setCreateDt(this.getSystemDt());
        iBase.setCreateId(loginUser.getLoginId());
    }

    public void setUpdateInfo(IBase iBase) {
        if (iBase == null) {
            return;
        }
        iBase.setUpdateDt(this.getSystemDt());
        iBase.setUpdateId(loginUser.getLoginId());
    }

    public void setDeleteInfo(IBase iBase) {
        if (iBase == null) {
            return;
        }
        iBase.setDeleteDt(this.getSystemDt());
        iBase.setDeleteId(loginUser.getLoginId());
    }
}
