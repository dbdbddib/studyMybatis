package com.softagape.mustacheajax.commons.inif;

public interface IMybatisCRUD<T> {
    void insert(T dto);
    void update(T dto);
    void updateDeleteFlag(T dto);
    void deleteById(Long id);
    T findById(Long id);
}
