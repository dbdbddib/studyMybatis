package com.studymavenspringboot.mybatisapp;

import java.util.List;

public interface ICommonService<T> {
    T findById(Long id);
    List<T> getAllList();
    T insert(T entity) throws Exception;
    Boolean delete(Long id) throws Exception;
    T update(Long id, T entity) throws Exception;
}
