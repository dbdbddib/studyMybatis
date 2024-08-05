package com.study.mustacheapp.category;
import com.study.mustacheapp.SearchAjaxDto;

import java.util.List;

public interface ICategoryService {
    ICategory findById(Long id);
    ICategory findByName(String name);
    List<ICategory> getAllList();
    ICategory insert(ICategory category) throws Exception;
    boolean remove(Long id) throws Exception;
    ICategory update(Long id, ICategory category) throws Exception;
    List<ICategory> findAllByNameContains(SearchAjaxDto dto);
    int countAllByNameContains(SearchAjaxDto searchAjaxDto);
}
