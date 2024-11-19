package com.softagape.mustacheajax.category;

import com.softagape.mustacheajax.commons.dto.SearchAjaxDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService<ICategory> {
    @Autowired  // SpringBoot 가 CategoryMybatisMapper 데이터형으로 객체를 자동 생성한다.
    private ICategoryMybatisMapper categoryMybatisMapper;

    @Override
    public ICategory findById(Long id) {
        if ( id == null || id <= 0 ) {
            return null;
        }
        CategoryDto find = this.categoryMybatisMapper.findById(id);
        // CategoryMybatisMapper 의 쿼리 XML 파일의 <select id="findById" 문장을 실행한 결과를 리턴 받는다.
        return find;
    }

    @Override
    public ICategory findByName(String name) {
        if ( name == null || name.isEmpty() ) {
            return null;
        }
        CategoryDto find = this.categoryMybatisMapper.findByName(name);
        // CategoryMybatisMapper 의 쿼리 XML 파일의 <select id="findByName" 문장을 실행한 결과를 리턴 받는다.
        return find;
    }

    @Override
    public List<ICategory> getAllList() {
        List<ICategory> list = this.getICategoryList(
                this.categoryMybatisMapper.findAll()
                // CategoryMybatisMapper 의 쿼리 XML 파일의 <select id="findAll" 문장을 실행한 결과를 리턴 받는다.
        );
        return list;
    }

    private List<ICategory> getICategoryList(List<CategoryDto> list) {
        if ( list == null || list.size() <= 0 ) {
            return new ArrayList<>();
        }
        // input : [CategoryDto|CategoryDto|CategoryDto|CategoryDto|CategoryDto]
//        List<ICategory> result = new ArrayList<>();
//        for( CategoryDto entity : list ) {
//            result.add( (ICategory)entity );
//        }
        // output : [ICategory|ICategory|ICategory|ICategory|ICategory]
        List<ICategory> result = list.stream()
                .map(entity -> (ICategory)entity)
                .toList();
        // stream Java 1.8 등장한 방법 : 배열/Collection 자료형을 처리할때
        // stream 을 사용하여 처리 속도도 증가하고 문법도 간결하게 만든다.
        // 배열객체/컬렉션자료형.stream() ~~~~ 정렬, 형변환, 원소마다 똑같은 동작을 처리한다.

        return result;
    }

    @Override
    public ICategory insert(ICategory category) {
        if ( !isValidInsert(category) ) {
            return null;
        }
        CategoryDto dto = new CategoryDto();
        dto.copyFields(category);
        dto.setId(0L);
        this.categoryMybatisMapper.insert(dto);
        // CategoryMybatisMapper 의 쿼리 XML 파일의 <insert id="insert" 문장을 실행한다.
        // dto.id 는 자동증가된 id 값이 리턴된다.
        return dto;
    }

    private boolean isValidInsert(ICategory category) {
        if ( category == null ) {
            return false;
        } else if ( category.getName() == null || category.getName().isEmpty() ) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean deleteById(Long id) {
        ICategory find = this.findById(id);
        if ( find == null ) {
            return false;
        }
        this.categoryMybatisMapper.deleteById(id);
        // CategoryMybatisMapper 의 쿼리 XML 파일의 <delete id="deleteById" 문장을 실행한다.
        return true;
    }

    @Override
    public ICategory update(ICategory dto) {
        if ( dto == null || dto.getId() == null || dto.getId() <= 0 ) {
            return null;
        }
        ICategory find = this.findById(dto.getId());
        if ( find == null ) {
            return null;
        }
        find.copyFields(dto);
        this.categoryMybatisMapper.update((CategoryDto) find);
        // CategoryMybatisMapper 의 쿼리 XML 파일의 <update id="update" 문장을 실행한다.
        return find;
    }

    @Override
    public List<ICategory> findAllByNameContains(SearchAjaxDto dto) {
        if ( dto == null ) {
            //return List.of();
            return new ArrayList<>();
        }
        dto.settingValues();
        List<ICategory> list = this.getICategoryList(
                this.categoryMybatisMapper.findAllByNameContains(dto)
                // CategoryMybatisMapper 의 쿼리 XML 파일의 <select id="findAllByNameContains" 문장을 실행한 결과를 리턴한다.
        );
        return list;
    }

    @Override
    public int countAllByNameContains(SearchAjaxDto searchAjaxDto) {
        return this.categoryMybatisMapper.countAllByNameContains(searchAjaxDto);
        // CategoryMybatisMapper 의 쿼리 XML 파일의 <select id="categoryMybatisMapper" 문장을 실행한 결과를 리턴한다.
    }
}
