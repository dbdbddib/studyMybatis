package com.studymavenspringboot.mybatisapp.category;

public interface ICategory {
    Long getId();
    void setId(Long id);

    String getName();
    void setName(String name);


    // 필드값 복사하는 메소드
    default void copyFields(ICategory from) {
        if (from == null) {
            return;
        }
        if (from.getId() != null) {
            this.setId(from.getId());
        }
        if (from.getName() != null && !from.getName().isEmpty()) {
            this.setName(from.getName());
        }
    }
}
