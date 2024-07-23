package com.studymavenspringboot.mybatisapp.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService{
    @Autowired
    private CategoryMybatisMapper categoryMybatisMapper;

    private boolean isValidInsert(ICategory dto) {
        if (dto == null) {
            return false;
        } else if (dto.getName() == null || dto.getName().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public ICategory findById(Long id) {
        if( id == null || id <= 0){
            return null;
        }
        CategoryDto dto = this.categoryMybatisMapper.findById(id);
        return dto;   // find의 값이 없으면 null로 리턴..
    }

    @Override
    public ICategory findByName(String name) {
        CategoryDto find = this.categoryMybatisMapper.findByName(name);
        return find;
    }

    @Override
    public List<ICategory> getAllList() {
        return this.getICategoryList(this.categoryMybatisMapper.findAll());
    }

    private List<ICategory> getICategoryList(List<CategoryDto> list) {
        if(list == null || list.size() <= 0){
            return new ArrayList<>();
        }
        return list.stream()
                .map(entity -> (ICategory)entity)
                .toList();
    }

    @Override
    public ICategory insert(ICategory category) throws Exception {
        if (!this.isValidInsert(category)) {
            return null;
        }
        CategoryDto dto = new CategoryDto();
        // 매개변수에서 받은 클래스의 필드값을 엔티티 클래스의 필드값으로 복붙
        // 엔티티로 변경하는 이유는 데이터베이스 테이블의 구조에 접근하기 위함
        // 실직적으로 데이터베이스에 접근할 수 있는 클래스는 엔티티 뿐?
        dto.copyFields(category);
        dto.setId(0L);
        this.categoryMybatisMapper.insert(dto);
        return dto;
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
    public List<ICategory> findAllByNameContains(String name) {
        if(name == null || name.isEmpty()){
            return new ArrayList<>();
        }
        return this.getICategoryList(
                this.categoryMybatisMapper.findAllByNameContains(name)
        );
    }

    @Override
    public List<ICategory> getListFromName(String findName) {
        if (findName == null || findName.isEmpty()) {
            return new ArrayList<>();
        }
//        List<CategoryEntity> list = this.categoryMybatisMapper.findAllByNameContains(findName);
//        List<ICategory> result = new ArrayList<>();
//        for(CategoryEntity item : list){
//            result.add((ICategory) item);
//        }
        return null;
    }
}
