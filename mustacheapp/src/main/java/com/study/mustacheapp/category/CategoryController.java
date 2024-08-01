package com.study.mustacheapp.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j      // log 만들어줌
@RestController // RestFul API 용
@RequestMapping("/ct")


public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @PostMapping
    public ResponseEntity<ICategory> insertC(@RequestBody CategoryRequest dto){
        try{
            if (dto==null) {
                return ResponseEntity.badRequest().build();
            }
            ICategory result = this.categoryService.insert(dto);
            if(result == null){
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(result);
        } catch (Exception ex){
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ICategory>> getAll() {
        try {
            List<ICategory> result = this.categoryService.getAllList();
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        try {
            if (id == null) {
                return ResponseEntity.badRequest().build();
            }
            Boolean result = this.categoryService.remove(id);
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ICategory> update(@PathVariable Long id, @RequestBody CategoryRequest dto) {
        try {
            if (id == null || dto == null) {
                return ResponseEntity.badRequest().build();
            }
            ICategory result = this.categoryService.update(id, dto);
            if (result == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ICategory> findById(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().build();
            }
            ICategory result = this.categoryService.findById(id);
            if (result == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<ICategory>> findAllByNameContains(@PathVariable String searchName) {
        try {
            if (searchName == null || searchName.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            SearchCategoryDto searchCategoryDto = SearchCategoryDto.builder()
                    .searchName(searchName).page(1).build();
            List<ICategory> result = this.categoryService.findAllByNameContains(searchCategoryDto);
            if ( result == null || result.size() <= 0 ) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/searchName")
    public ResponseEntity<SearchCategoryDto> findAllByNameContains(@RequestBody SearchCategoryDto searchCategoryDto) {
        try {
            if ( searchCategoryDto == null ) {
                return ResponseEntity.badRequest().build();
            }
            int total = this.categoryService.countAllByNameContains(searchCategoryDto);
            List<ICategory> list = this.categoryService.findAllByNameContains(searchCategoryDto);
            if ( list == null ) {
                return ResponseEntity.notFound().build();
            }
            searchCategoryDto.setTotal(total);
            searchCategoryDto.setDataList(list);
            return ResponseEntity.ok(searchCategoryDto);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/countName")
    public ResponseEntity<Integer> countAllByNameContains(@RequestBody SearchCategoryDto searchCategoryDto) {
        try {
            if ( searchCategoryDto == null ) {
                return ResponseEntity.badRequest().build();
            }
            int total = this.categoryService.countAllByNameContains(searchCategoryDto);
            return ResponseEntity.ok(total);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }
}
