package com.softagape.mustacheajax.category;

import com.softagape.mustacheajax.commons.dto.ResponseCode;
import com.softagape.mustacheajax.commons.dto.ResponseDto;
import com.softagape.mustacheajax.commons.dto.SearchAjaxDto;
import com.softagape.mustacheajax.commons.inif.IResponseController;
import com.softagape.mustacheajax.member.IMember;
import com.softagape.mustacheajax.security.config.SecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j  // log 를 만들어 준다.
@RestController // RestFul API 용 Controller 이다. JSON 문자형식으로 요청/응답 한다.
@RequestMapping("/api/vi/cat")  // Controller 의 url 앞부분이다.
public class CategoryWebRestController implements IResponseController {
    @Autowired  // SpringBoot 가 CategoryServiceImpl 데이터형으로 객체를 자동 생성한다.
    private CategoryServiceImpl categoryService;

    @PostMapping    // POST method : /api/vi/cat
    public ResponseEntity<ResponseDto> insert(@RequestBody CategoryDto dto) {
        // ResponseEntity<데이터형> : http 응답을 http 응답코드와 리턴데이터형으로 묶어서 응답한다.
        // @RequestBody CategoryDto dto : JSON 문자열로 요청을 받는다.
        //      다만 JSON 문자열의 데이터가 CategoryDto 데이터형이어야 한다.
        try {
            if ( dto == null ) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            ICategory result = this.categoryService.insert(dto);
            // 최종 목적지인 Mybatis 쿼리를 DB 에 실행하고 결과를 리턴 받는다.
            if ( result == null ) {
                return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R000011, "서버 입력 에러", null);
            }
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", result);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }

    @PatchMapping("/{id}")     // PATCH method : /api/vi/cat/번호
    public ResponseEntity<ResponseDto> update(@PathVariable Long id, @RequestBody CategoryDto dto) {
        // ResponseEntity<데이터형> : http 응답을 http 응답코드와 리턴데이터형으로 묶어서 응답한다.
        // @PathVariable Long id : URL 주소의 /api/vi/cat/번호 => {id} id 변수의 값으로 요청된다.
        // @RequestBody CategoryDto dto : JSON 문자열로 요청을 받는다.
        //      다만 JSON 문자열의 데이터가 CategoryDto 데이터형이어야 한다. {"id":값, "name":"값"}
        try {
            if ( id == null || id <= 0 || dto == null || dto.getId() == null || !id.equals(dto.getId()) ) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            ICategory result = this.categoryService.update(dto);
            // 최종 목적지인 Mybatis 쿼리를 DB 에 실행하고 결과를 리턴 받는다.
            if ( result == null ) {
                return ResponseEntity.notFound().build(); // error 응답
            }
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", result);
            // 200 OK 와 result 데이터를 응답한다.
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }

    @DeleteMapping("/{id}")     // DELETE method : /api/vi/cat/번호
    public ResponseEntity<ResponseDto> delete(@PathVariable Long id) {
        // ResponseEntity<데이터형> : http 응답을 http 응답코드와 리턴데이터형으로 묶어서 응답한다.
        // @PathVariable Long id : URL 주소의 /api/vi/cat/번호 => {id} id 변수의 값으로 요청된다.
        try {
            if ( id == null ) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            Boolean result = this.categoryService.deleteById(id);
            // 최종 목적지인 Mybatis 쿼리를 DB 에 실행하고 결과를 리턴 받는다.
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", result);
            // 200 OK 와 result 데이터를 응답한다.
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> findById(@PathVariable Long id) {
        try {
            if ( id == null || id <= 0 ) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            ICategory find = this.categoryService.findById(id);
            // 최종 목적지인 Mybatis 쿼리를 DB 에 실행하고 결과를 리턴 받는다.
            if ( find == null ) {
                return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, "데이터 검색 에러", null);
            }
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", find);
            // 200 OK 와 result 데이터를 응답한다.
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }

    @GetMapping     // GET method : /api/vi/cat
    public ResponseEntity<ResponseDto> getAll() {
        // ResponseEntity<데이터형> : http 응답을 http 응답코드와 리턴데이터형으로 묶어서 응답한다.
        // List<ICategory> 데이터형 리턴은 배열 데이터를 JSON 문자열로 표현하여 리턴한다.
        // [{"id":값, "name":"값"}, {"id":값, "name":"값"}, {"id":값, "name":"값"}, ...]
        try {
            List<ICategory> result = this.categoryService.getAllList();
            // 최종 목적지인 Mybatis 쿼리를 DB 에 실행하고 결과를 리턴 받는다.
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", result);
            // 200 OK 와 result 데이터를 응답한다.
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }

    @PostMapping("/countName")  // POST method : /api/vi/cat/countName
    public ResponseEntity<ResponseDto> countAllByNameContains(Model model, @RequestBody SearchAjaxDto searchAjaxDto) {
        // ResponseEntity<데이터형> : http 응답을 http 응답코드와 리턴데이터형으로 묶어서 응답한다.
        // @RequestBody SearchAjaxDto searchAjaxDto : JSON 문자열로 요청을 받는다.
        //      다만 JSON 문자열의 데이터가 SearchAjaxDto 데이터형이어야 한다.
        //      {"searchName":"값"}
        try {
            IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
            // POSTMAN 으로 테스트 안되지만, WEB 화면에서는 로그인한 사용자만 가능하다.
            if ( loginUser == null ) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, "로그인 필요", null);
            }
            if ( searchAjaxDto == null ) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            int total = this.categoryService.countAllByNameContains(searchAjaxDto);
            // 최종 목적지인 Mybatis 쿼리를 DB 에 실행하고 결과를 리턴 받는다.
            // countAllByNameContains 쿼리 문장을 만들때 searchName 값을 활용하여 쿼리 문장을 만들고 실행한다.
            // 데이터 행수를 리턴한다.
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", total);
            // 200 OK 와 result 데이터를 응답한다.
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }

    @PostMapping("/searchName") // POST method : /api/vi/cat/searchName
    public ResponseEntity<ResponseDto> findAllByNameContains(Model model, @RequestBody SearchAjaxDto searchAjaxDto) {
        // ResponseEntity<데이터형> : http 응답을 http 응답코드와 리턴데이터형으로 묶어서 응답한다.
        // SearchAjaxDto 데이터형를 JSON 문자열로 표현하여 리턴한다.
        // @RequestBody SearchAjaxDto searchAjaxDto : JSON 문자열로 요청을 받는다.
        //      다만 JSON 문자열의 데이터가 SearchAjaxDto 데이터형이어야 한다.
        //      {"searchName":"값", "sortColumn":"값", "sortAscDsc":"값", "page":값}
        try {
            IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
            // POSTMAN 으로 테스트 안되지만, WEB 화면에서는 로그인한 사용자만 가능하다.
            if ( loginUser == null ) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, "로그인 필요", null);
            }
            if ( searchAjaxDto == null ) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            int total = this.categoryService.countAllByNameContains(searchAjaxDto);
            // 최종 목적지인 Mybatis 쿼리를 DB 에 실행하고 결과를 리턴 받는다.
            // 검색식의 searchName 으로 찾은 데이터 행수를 리턴받는다. 화면의 페이지 계산에 사용된다.
            List<ICategory> list = this.categoryService.findAllByNameContains(searchAjaxDto);
            // 최종 목적지인 Mybatis 쿼리를 DB 에 실행하고 결과를 리턴 받는다.
            // findAllByNameContains 쿼리 문장을 만들때 orderByWord, searchName, rowsOnePage, firstIndex 값을
            // 활용하여 쿼리 문장을 만들고 실행한다.
            if ( list == null ) {
                return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, "데이터 검색 에러", null);
            }
            searchAjaxDto.setTotal(total);
            // SearchAjaxDto 응답결과에 total 을 추가한다.
            searchAjaxDto.setDataList(list);
            // SearchAjaxDto 응답결과에 List<ICategory> 을 추가한다.
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", searchAjaxDto);
            // 200 OK 와 result 데이터를 응답한다.
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }
}
