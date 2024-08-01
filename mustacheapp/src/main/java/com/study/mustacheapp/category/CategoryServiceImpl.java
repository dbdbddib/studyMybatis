package com.study.mustacheapp.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService{
    @Autowired
    private CategoryMybatisMapper categoryMybatisMapper;

    @Override
    public ICategory findById(Long id) {
        if( id == null || id <= 0){
            return null;
        }
        CategoryDto find = this.categoryMybatisMapper.findById(id);
        return find;
    }

    @Override
    public ICategory findByName(String name) {
        if ( name == null || name.isEmpty() ) {
            return null;
        }
        CategoryDto find = this.categoryMybatisMapper.findByName(name);
        return find;
    }

    @Override
    public List<ICategory> getAllList() {
        List<ICategory> list = this.getICategoryList(
                this.categoryMybatisMapper.findAll()
        );
        return list;
    }

    private List<ICategory> getICategoryList(List<CategoryDto> list) {
        if ( list == null || list.size() <= 0 ) {
            return new ArrayList<>();
        }
        List<ICategory> result = list.stream()
                .map(entity -> (ICategory)entity)
                .toList();
        // return : [ICategory|ICategory|ICategory|ICategory|ICategory]
        return result;
    }

    @Override
    public ICategory insert(ICategory category) throws Exception {
        if ( !isValidInsert(category) ) {
            return null;
        }
        CategoryDto dto = new CategoryDto();
        dto.copyFields(category);
        dto.setId(0L);
        this.categoryMybatisMapper.insert(dto);
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
    public boolean remove(Long id) throws Exception {
        ICategory find = this.findById(id);
        if (find == null) {
            return false;
        }
        this.categoryMybatisMapper.deleteById(id);
        return true;
    }

    @Override
    public ICategory update(Long id, ICategory category) throws Exception {
        ICategory find = this.findById(id);
        if (find == null) {
            return null;
        }
        find.copyFields(category);
        this.categoryMybatisMapper.update((CategoryDto) find);
        return find;
    }

    @Override
    public List<ICategory> findAllByNameContains(SearchCategoryDto dto) {
        if ( dto == null ) {
            //return List.of();
            return new ArrayList<>();
        }
        String sOrer = "";
        if ( dto.getSortColumn() == null ) {
            sOrer = "id";
        } else {
            sOrer = dto.getSortColumn();
        }
        dto.setOrderByWord( sOrer + " " + dto.getSortAscDsc() );
        if ( dto.getRowsOnePage() == null ) {
            dto.setRowsOnePage(10);
        }
        List<ICategory> list = this.getICategoryList(
                this.categoryMybatisMapper.findAllByNameContains(dto)
        );
        return list;
    }

    @Override
    public int countAllByNameContains(SearchCategoryDto searchCategoryDto) {
        return this.categoryMybatisMapper.countAllByNameContains(searchCategoryDto);
    }
}
