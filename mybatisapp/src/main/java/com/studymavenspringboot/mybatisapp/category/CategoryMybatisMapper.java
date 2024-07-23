package com.studymavenspringboot.mybatisapp.category;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMybatisMapper {
    void insert(CategoryDto dto);

    CategoryDto findById(long id);

    CategoryDto findByName(String name);

    List<CategoryDto> findAll();

    void deleteById(Long id);

    void update(CategoryDto dto);

    List<CategoryDto> findAllByNameContains(String name);
}
