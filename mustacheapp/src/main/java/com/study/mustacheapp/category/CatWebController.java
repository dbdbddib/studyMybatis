package com.study.mustacheapp.category;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/catweb")
public class CatWebController {
    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("")
    public String indexHome() {
        return "index";
    }

    @GetMapping("/category_list")    // 브라우저의 URL 주소
    public String categoryOld(Model model, @RequestParam String searchName, @RequestParam int page) {
        try {
            if (searchName == null) {
                searchName = "";
            }
//            List<ICategory> allList = this.categoryService.getAllList();
            SearchCategoryDto searchCategoryDto = SearchCategoryDto.builder()
                    .page(page).searchName(searchName).build();
            int count = this.categoryService.countAllByNameContains(searchCategoryDto);
            searchCategoryDto.setTotal(count);
            List<ICategory> allList = this.categoryService.findAllByNameContains(searchCategoryDto);
            // model 을 사용하는 이유 화면을 만들기 위해, allList, searchCategoryDto 를 준다 그러므로 category_list.html 에서 사용가능
            model.addAttribute("allList", allList);
            model.addAttribute("searchCategoryDto", searchCategoryDto);
        } catch (Exception ex) {
            log.error(ex.toString());
            model.addAttribute("error_message", "오류가 발생했습니다. 관리자에게 문의하세요.");
            return "error/error_save";  // resources/templates 폴더안의 화면파일
        }
        return "catweb/category_list";
    }

    @GetMapping("/category_add")    // 브라우저의 URL 주소
    public String categoryAdd() {
        return "catweb/category_add";  // resources/templates 폴더안의 화면파일
    }

    @PostMapping("/category_insert")
    public String categoryInsert(@ModelAttribute CategoryDto dto, Model model) {
        try {
            if ( dto == null || dto.getName() == null || dto.getName().isEmpty() ) {
                model.addAttribute("error_message", "이름이 비었습니다.");
                return "error/error_bad";  // resources/templates 폴더안의 화면파일
            }
            this.categoryService.insert(dto);
        }catch (Exception ex) {
            log.error(ex.toString());
            model.addAttribute("error_message", dto.getName() + " 중복입니다.");
            return "error/error_save";  // resources/templates 폴더안의 화면파일
        }
        return "redirect:category_list?page=1&searchName=";
    }

    @GetMapping("/category_search")
    public String categorySearch(@RequestParam String searchName) throws UnsupportedEncodingException {
        String encodedName = URLEncoder.encode(searchName, "UTF-8");  // get 방식 url 한글인코딩
        return "redirect:category_list?page=1&searchName=" + encodedName;
    }

    @GetMapping("/category_view")    // 브라우저의 URL 주소
    public String categoryView(Model model, @RequestParam Long id) {
        try {
            if ( id == null || id <= 0 ) {
                model.addAttribute("error_message", "ID는 1보다 커야 합니다.");
                return "error/error_bad";  // resources/templates 폴더안의 화면파일
            }
            ICategory find = this.categoryService.findById(id);
            if ( find == null ) {
                model.addAttribute("error_message", id + " 데이터가 없습니다.");
                return "error/error_find";
            }
            model.addAttribute("categoryDto", find);
        } catch (Exception ex) {
            log.error(ex.toString());
            model.addAttribute("error_message", "서버 에러입니다. 관리자에게 문의 하세요.");
            return "error/error_save";  // resources/templates 폴더안의 화면파일
        }
        return "catweb/category_view";  // resources/templates 폴더안의 화면파일
    }

    @PostMapping("/category_update")
    public String categoryupdate(Model model, @ModelAttribute CategoryDto categoryDto) {
        try {
            if (categoryDto == null || categoryDto.getId() <= 0 || categoryDto.getName().isEmpty()) {
                model.addAttribute("error_message", "id는 1보다 커야하고, name 이 있어야 합니다.");
                return "error/error_bad";  // resources/templates 폴더안의 화면파일
            }
            ICategory find = this.categoryService.findById(categoryDto.getId());
            if (find == null) {
                model.addAttribute("error_message", categoryDto.getId() + " 데이터가 없습니다.");
                return "error/error_find";
            }
            this.categoryService.update(categoryDto.getId(), categoryDto);
        } catch (Exception ex) {
            log.error(ex.toString());
            model.addAttribute("error_message", categoryDto.getName() + " 중복입니다.");
            return "error/error_save";  // resources/templates 폴더안의 화면파일
        }
        return "redirect:category_list?page=1&searchName=";
    }

    @GetMapping("/category_delete")
    public String categoryDelete(Model model, @RequestParam Long id) {
        try {
            if (id == null || id <= 0) {
                model.addAttribute("error_message", "id는 1보다 커야 합니다.");
                return "error/error_bad";  // resources/templates 폴더안의 화면파일
            }
            ICategory find = this.categoryService.findById(id);
            if (find == null) {
                model.addAttribute("error_message", id + " 데이터가 없습니다.");
                return "error/error_find";
            }
            this.categoryService.remove(id);
        } catch (Exception ex) {
            log.error(ex.toString());
            model.addAttribute("error_message", "서버 에러입니다. 관리자에게 문의 하세요.");
            return "error/error_save";  // resources/templates 폴더안의 화면파일
        }
        return "redirect:category_list?page=1&searchName=";
    }
}
