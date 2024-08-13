package com.study.mustacheapp.category;

import com.study.mustacheapp.commons.dto.SearchAjaxDto;
import com.study.mustacheapp.member.IMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j      // log 만들어줌
@RestController // RestFul API 용 Controller 이다. JSON 문자형식으로 요청/응답 한다.
@RequestMapping("/ct")


public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @PostMapping    // POST method : /ct
    public ResponseEntity<ICategory> insert(@RequestBody CategoryDto dto) {
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
            Boolean result = this.categoryService.delete(id);
            // 최종 목적지인 Mybatis 쿼리를 DB 에 실행하고 결과를 리턴 받는다.
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}")     // PATCH method : /ct/번호
    public ResponseEntity<ICategory> update(@PathVariable Long id, @RequestBody CategoryDto dto) {
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

    @GetMapping("/nm/{searchName}") // POST method : /ct/nm/문자열{searchName}
    public ResponseEntity<List<ICategory>> findAllByNameContains(@PathVariable String searchName) {
        try {
            if (searchName == null || searchName.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            SearchAjaxDto searchAjaxDto = SearchAjaxDto.builder()
                    .searchName(searchName).page(1).build();
            List<ICategory> result = this.categoryService.findAllByNameContains(searchAjaxDto);
            if ( result == null || result.size() <= 0 ) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/searchName") // POST method : /ct/searchName
    public ResponseEntity<SearchAjaxDto> findAllByNameContains(Model model, @RequestBody SearchAjaxDto searchAjaxDto) {
        // ResponseEntity<데이터형> : http 응답을 http 응답코드와 리턴데이터형으로 묶어서 응답한다.
        // SearchAjaxDto 데이터형를 JSON 문자열로 표현하여 리턴한다.
        // @RequestBody SearchAjaxDto searchAjaxDto : JSON 문자열로 요청을 받는다.
        //      다만 JSON 문자열의 데이터가 SearchAjaxDto 데이터형이어야 한다.
        //      {"searchName":"값", "sortColumn":"값", "sortAscDsc":"값", "page":값}
        try {
            IMember loginUser = (IMember)model.getAttribute("loginUser");
            // POSTMAN 으로 테스트 안되지만, WEB 화면에서는 로그인한 사용자만 가능하다.
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            if ( searchAjaxDto == null ) {
                return ResponseEntity.badRequest().build();
            }
            int total = this.categoryService.countAllByNameContains(searchAjaxDto);
            List<ICategory> list = this.categoryService.findAllByNameContains(searchAjaxDto);
            if ( list == null ) {
                return ResponseEntity.notFound().build();
            }
            searchAjaxDto.setTotal(total);
            searchAjaxDto.setDataList(list);
            return ResponseEntity.ok(searchAjaxDto);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/countName")  // POST method : /ct/countName
    public ResponseEntity<Integer> countAllByNameContains(Model model, @RequestBody SearchAjaxDto searchAjaxDto) {
        // ResponseEntity<데이터형> : http 응답을 http 응답코드와 리턴데이터형으로 묶어서 응답한다.
        // @RequestBody SearchAjaxDto searchAjaxDto : JSON 문자열로 요청을 받는다.
        //      다만 JSON 문자열의 데이터가 SearchAjaxDto 데이터형이어야 한다.
        //      {"searchName":"값"}
        try {
            IMember loginUser = (IMember)model.getAttribute("loginUser");
            // POSTMAN 으로 테스트 안되지만, WEB 화면에서는 로그인한 사용자만 가능하다.
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            if ( searchAjaxDto == null ) {
                return ResponseEntity.badRequest().build();
            }
            int total = this.categoryService.countAllByNameContains(searchAjaxDto);
            return ResponseEntity.ok(total);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }
}
