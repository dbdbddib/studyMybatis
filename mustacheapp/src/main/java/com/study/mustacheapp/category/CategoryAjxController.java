package com.study.mustacheapp.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j  // log 를 만들어 준다.
@Controller // Web 용 Controller 이다. 화면을 그리거나 redirect 할때 유용하다.
@RequestMapping("/catajx")  // Controller 의 url 앞부분이다.
public class CategoryAjxController {

    @GetMapping("/category_ajx_list")
    // GET method로 /catajx/~~~~~~~~~ url 주소로 접속하도록 한다.
    public String category_ajx_list() {
        return "catajx/category_ajx_list";
        // 화면 템플릿 엔진의 화면파일 경로/파일명
        // => resources/templates/catajx/category_ajx_list.html
    }
}
