package com.study.mustacheapp.category;
import com.study.mustacheapp.SearchAjaxDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
// Mybatis 쿼리를 선언한 xml 파일의 함수와 연결한다.
public interface CategoryMybatisMapper {
    void insert(CategoryDto dto);

    void update(CategoryDto dto);

    void deleteById(Long id);

    CategoryDto findById(long id);

    CategoryDto findByName(String name);

    List<CategoryDto> findAll();

    int countAllByNameContains(SearchAjaxDto searchAjaxDto);
    List<CategoryDto> findAllByNameContains(SearchAjaxDto searchAjaxDto);
}


// jpa 에서는 jpaRepository  Mybatis는 Mapper  로 db 접근
// 쿼리 선언한 xml 파일의 함수와 연결 -> 2가지 방법이 있다 
// 1. MybatisConfig 처럼 클래스 선언
// 2. application.properties 에 선언