package com.softagape.mustacheajax.category;

import com.softagape.mustacheajax.commons.dto.SearchAjaxDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
// Mybatis 쿼리를 선언한 xml 파일의 함수와 연결한다.
public interface ICategoryMybatisMapper {
    void insert(CategoryDto dto);
    void update(CategoryDto dto);
    void deleteById(Long id);
    CategoryDto findById(Long id);

    CategoryDto findByName(String name);
    List<CategoryDto> findAll();

    Integer countAllByNameContains(SearchAjaxDto searchAjaxDto);
    List<CategoryDto> findAllByNameContains(SearchAjaxDto searchAjaxDto);
}
