package com.study.mustacheapp.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/catajx")
public class CategoryAjxController {

    @GetMapping("category_ajx_list")
    public String category_ajx_list() {
        return "catajx/category_ajx_list";
    }
}
