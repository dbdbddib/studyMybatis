package com.studymavenspringboot.mybatisapp.category;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMybatisMapper {
    void insert(CategoryDto dto);

    void update(CategoryDto dto);

    void deleteById(Long id);

    CategoryDto findById(long id);

    CategoryDto findByName(String name);

    List<CategoryDto> findAll();

    int countAllByNameContains(SearchCategoryDto searchCategoryDto);
    List<CategoryDto> findAllByNameContains(SearchCategoryDto searchCategoryDto);
}
