package com.study.mustacheapp.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/catajx")
public class CategoryAjxController {

    // GET method로 /catajx/category_ajx_list   url 주소로 접속
    @GetMapping("category_ajx_list")

    public String category_ajx_list() {

        return "catajx/category_ajx_list";
        // 기본값 resources/templates/ 아레에서 .html 찾는다.
    }
}
