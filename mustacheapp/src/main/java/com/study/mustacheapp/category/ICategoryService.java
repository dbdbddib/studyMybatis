package com.study.mustacheapp.category;

import com.study.mustacheapp.ICommonService;
import com.study.mustacheapp.commons.dto.SearchAjaxDto;

import java.util.List;

public interface ICategoryService<T> extends ICommonService<T> {
    ICategory findByName(String name);
    List<ICategory> findAllByNameContains(SearchAjaxDto dto);
    int countAllByNameContains(SearchAjaxDto searchAjaxDto);
}
