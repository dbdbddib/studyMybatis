package com.softagape.mustacheajax.member;


import com.softagape.mustacheajax.commons.dto.IBase;

public interface IMember extends IBase {
    Long getId();
    void setId(Long id);

    String getName();
    void setName(String name);

    String getNickname();
    void setNickname(String nickname);

    String getLoginId();
    void setLoginId(String loginId);

    String getPassword();
    void setPassword(String password);

    String getEmail();
    void setEmail(String email);

    String getRole();
    void setRole(String role);

    Boolean getActive();
    void setActive(Boolean active);

    default void copyFields(IMember from) {
        if (from == null) {
            return;
        }
        if (from.getId() != null) {
            this.setId(from.getId());
        }
        if (from.getName() != null && !from.getName().isEmpty()) {
            this.setName(from.getName());
        }
        if (from.getNickname() != null && !from.getNickname().isEmpty()) {
            this.setNickname(from.getNickname());
        }
        if (from.getLoginId() != null && !from.getLoginId().isEmpty()) {
            this.setLoginId(from.getLoginId());
        }
        if (from.getPassword() != null && !from.getPassword().isEmpty()) {
            this.setPassword(from.getPassword());
        }
        if (from.getEmail() != null && !from.getEmail().isEmpty()) {
            this.setEmail(from.getEmail());
        }
        if (from.getRole() != null && !from.getRole().isEmpty()) {
            this.setRole(from.getRole());
        }
        if (from.getActive() != null) {
            this.setActive(from.getActive());
        }
        IBase.super.copyFields(from);
    }
}
