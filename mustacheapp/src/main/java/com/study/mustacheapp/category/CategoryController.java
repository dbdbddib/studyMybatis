package com.study.mustacheapp.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j      // log 만들어줌
@RestController // RestFul API 용 Controller 이다. JSON 문자형식으로 요청/응답 한다.
@RequestMapping("/ct")


public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @PostMapping
    public ResponseEntity<ICategory> insertC(@RequestBody CategoryRequest dto){
        // ResponseEntity<데이터형> : http 응답을 http 응답코드와 리턴데이터형으로 묶어서 응답한다.
        // @RequestBody CategoryDto dto : JSON 문자열로 요청을 받는다.
        //      다만 JSON 문자열의 데이터가 CategoryDto 데이터형이어야 한다.
        try{
            if (dto==null) {
                return ResponseEntity.badRequest().build();
            }
            ICategory result = this.categoryService.insert(dto);
            // 최종 목적지인 Mybatis 쿼리를 DB 에 실행하고 결과를 리턴 받는다.
            if(result == null){
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(result);
            // 200 OK 와 result 데이터를 응답한다.
        } catch (Exception ex){
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ICategory>> getAll() {
        // ResponseEntity<데이터형> : http 응답을 http 응답코드와 리턴데이터형으로 묶어서 응답한다.
        // List<ICategory> 데이터형 리턴은 배열 데이터를 JSON 문자열로 표현하여 리턴한다.
        // [{"id":값, "name":"값"}, {"id":값, "name":"값"}, {"id":값, "name":"값"}, ...]
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
        // ResponseEntity<데이터형> : http 응답을 http 응답코드와 리턴데이터형으로 묶어서 응답한다.
        // @PathVariable Long id : URL 주소의 /ct/번호 => {id} id 변수의 값으로 요청된다.
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
        // ResponseEntity<데이터형> : http 응답을 http 응답코드와 리턴데이터형으로 묶어서 응답한다.
        // @PathVariable Long id : URL 주소의 /ct/번호 => {id} id 변수의 값으로 요청된다.
        // @RequestBody CategoryDto dto : JSON 문자열로 요청을 받는다.
        //      다만 JSON 문자열의 데이터가 CategoryDto 데이터형이어야 한다. {"id":값, "name":"값"}
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
