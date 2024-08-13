package com.study.mustacheapp.commons.inif;

public interface IMybatisCRUD<T> {
    void insert(T dto);
    void update(T dto);
    void deleteFlag(T dto);
    void deleteById(Long id);
    T findById(Long id);
}
