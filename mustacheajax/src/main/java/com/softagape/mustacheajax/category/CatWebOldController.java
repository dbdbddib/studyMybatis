package com.softagape.mustacheajax.category;

import com.softagape.mustacheajax.commons.dto.SearchAjaxDto;
import com.softagape.mustacheajax.member.IMember;
import com.softagape.mustacheajax.security.config.SecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j  // log 를 만들어 준다.
@Controller // Web 용 Controller 이다. 화면을 그리거나 redirect 할때 유용하다.
@RequestMapping("/catweb")  // Controller 의 url 앞부분이다.
public class CatWebOldController {
    @Autowired  // SpringBoot 가 CategoryServiceImpl 데이터형으로 객체를 자동 생성한다.
    private CategoryServiceImpl categoryService;

    @GetMapping("/category_list")
    // GET method로 /catweb/category_list url 주소로 요청을 받도록 한다.
    public String categoryList(Model model, @RequestParam int page, @RequestParam String searchName) {
        // String : "템플릿 화면파일 경로", "redirect:url 주소"
        // Model model : MVC 프레임워크에서는 View 와 Controller 와 Model 을 분리해서 사용한다.
        //  View 와 Model 의 데이터를 연결하는 역할을 한다. 구식의 ModelAndView
        // @RequestParam int page, @RequestParam String searchName : HTTP Request Query String
        //  : url 주소에서 ?searchName=&page=값 변수의 값을 받는다. Request Query String
        try {
            IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
            if ( loginUser == null ) {
                // 로그인 사용자가 아니면 리턴
                return "redirect:/";
            }
            SearchAjaxDto searchAjaxDto = SearchAjaxDto.builder()
                    .page(page).searchName(searchName).build();
            // SearchAjaxDto 는 select Sql 쿼리문장을 만들때 where, order by, 페이지 문장을 만들때 사용한다.
            int total = this.categoryService.countAllByNameContains(searchAjaxDto);
            // 최종 목적지인 Mybatis 쿼리를 DB 에 countAllByNameContains 실행하고 결과를 리턴 받는다.
            // 검색식의 searchName 으로 찾은 데이터 행수를 리턴받는다. 화면의 페이지 계산에 사용된다.
            List<ICategory> list = this.categoryService.findAllByNameContains(searchAjaxDto);            // 최종 목적지인 Mybatis 쿼리를 DB 에 실행하고 결과를 리턴 받는다.
            // findAllByNameContains 쿼리 문장을 만들때 orderByWord, searchName, rowsOnePage, firstIndex 값을
            // 활용하여 쿼리 문장을 만들고 실행한다.
            searchAjaxDto.setTotal(total);
            // searchAjaxDto.total 값을 저장한다.
            model.addAttribute("categoryList", list);
            // Model 객체의 속성"categoryList" 과 list 값을 추가한다.
            // 화면템플릿 "catweb/category_list.html"의 속성이름"categoryList" 에서 list 값을 받는다.)
            model.addAttribute("searchAjaxDto", searchAjaxDto);
            // Model 객체의 속성"searchAjaxDto" 과 searchAjaxDto 값을 추가한다.
            // 화면템플릿 "catweb/category_list.html"의 속성이름"searchAjaxDto" 에서 searchAjaxDto 값을 받는다.)
        } catch (Exception ex) {
            log.error(ex.toString()); // error 응답
        }
        return "catweb/category_list";
        // 화면 템플릿 엔진의 화면파일 경로/파일명
        // => resources/templates/catweb/category_list.(html/mustache/jsp/..)
    }

    @GetMapping("/category_add")
    // GET method로 /catweb/category_add url 주소로 요청을 받도록 한다.
    public String categoryAdd(Model model) {
        // String : "템플릿 화면파일 경로", "redirect:url 주소"
        // Model model : MVC 프레임워크에서는 View 와 Controller 와 Model 을 분리해서 사용한다.
        //  View 와 Model 의 데이터를 연결하는 역할을 한다. 구식의 ModelAndView
        try {

        } catch (Exception ex) {
            log.error(ex.toString()); // error 응답
        }
        return "catweb/category_add";
        // 화면 템플릿 엔진의 화면파일 경로/파일명
        // => resources/templates/catweb/category_add.(html/mustache/jsp/..)
    }

    @PostMapping("/category_insert")
    // POST method로 /catweb/category_insert url 주소로 요청을 받도록 한다.
    public String categoryInsert(@ModelAttribute CategoryDto dto) {
        // String : "템플릿 화면파일 경로", "redirect:url 주소"
        // @ModelAttribute CategoryDto dto : POST 방식의 요청은 값이 숨겨져 있다. HTTP Request web form
        //  :  주소에서 ?searchName=&page=값 변수의 값을 받는다. "application/x-www-form-?????"
        try {
            this.categoryService.insert(dto);
            // categoryService Impl insert 메소드 실행, 매개변수 CategoryDto
        } catch (Exception ex) {
            log.error(ex.toString()); // error 응답
        }
        return "redirect:category_list?page=1&searchName=";
        // 화면 템플릿 엔진의 화면파일 경로/파일명
        // => redirect : 브라우저의 상대주소(category_list?page=1&searchName=)를 리다이렉트 한다.
    }

    @GetMapping("/category_view")
    // GET method로 /catweb/category_view url 주소로 요청을 받도록 한다.
    public String categoryView(Model model, @RequestParam Long id) {
        // String : "템플릿 화면파일 경로", "redirect:url 주소"
        // Model model : MVC 프레임워크에서는 View 와 Controller 와 Model 을 분리해서 사용한다.
        //  View 와 Model 의 데이터를 연결하는 역할을 한다. 구식의 ModelAndView
        // @RequestParam Long id : HTTP Request Query Parameter String
        //  : url 주소에서 ?id=값 변수의 값을 받는다.
        try {
            ICategory categoryDto = this.categoryService.findById(id);
            // categoryService Ipml 의 findById 를 실행하여 id 에 해당하는 자료를 찾아서 리턴한다.
            model.addAttribute("categoryDto", categoryDto);
            // Model 객체의 속성"categoryDto" 과 categoryDto 값을 추가한다.
            // 화면템플릿 "catweb/category_view.html"의 속성이름"categoryDto" 에서 categoryDto 값을 받는다.)
        } catch (Exception ex) {
            log.error(ex.toString()); // error 응답
        }
        return "catweb/category_view";
        // 화면 템플릿 엔진의 화면파일 경로/파일명
        // => resources/templates/catweb/category_view.(html/mustache/jsp/..)
    }

    @PostMapping("/category_update")
    public String categoryUpdate(@ModelAttribute CategoryDto dto) {
        // String : "템플릿 화면파일 경로", "redirect:url 주소"
        // @ModelAttribute CategoryDto dto : POST 방식의 요청은 값이 숨겨져 있다. HTTP Request web form
        //  :  주소에서 ?searchName=&page=값 변수의 값을 받는다. "application/x-www-form-?????"
        try {
            this.categoryService.update(dto);
            // categoryService Ipml 의 update 를 실행한다.
        } catch (Exception ex) {
            log.error(ex.toString()); // error 응답
        }
        return "redirect:category_list?page=1&searchName=";
        // 화면 템플릿 엔진의 화면파일 경로/파일명
        // => redirect : 브라우저의 상대주소(category_list?page=1&searchName=)를 리다이렉트 한다.
    }

    @GetMapping("/category_delete")
    public String categoryDelete(@RequestParam Long id) {
        // String : "템플릿 화면파일 경로", "redirect:url 주소"
        // @RequestParam Long id : HTTP Request Query Parameter String
        //  : url 주소에서 ?id=값 변수의 값을 받는다.
        try {
            this.categoryService.deleteById(id);
            // categoryService Ipml 의 delete 를 실행한다.
        } catch (Exception ex) {
            log.error(ex.toString()); // error 응답
        }
        return "redirect:/catweb/category_list?page=1&searchName=";
        // 화면 템플릿 엔진의 화면파일 경로/파일명
        // => redirect : 브라우저의 절대주소(/catweb/category_list?page=1&searchName=)를 리다이렉트 한다. 절대주소를 사용하지 말자.
    }
}
