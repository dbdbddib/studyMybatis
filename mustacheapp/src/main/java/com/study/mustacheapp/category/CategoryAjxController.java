package com.study.mustacheapp.category;

import com.study.mustacheapp.member.IMember;
import com.study.mustacheapp.member.IMemberService;
import com.study.mustacheapp.member.MemberRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j  // log 를 만들어 준다.
@Controller // Web 용 Controller 이다. 화면을 그리거나 redirect 할때 유용하다.
@RequestMapping("/catajx")  // Controller 의 url 앞부분이다.
public class CategoryAjxController {
    @Qualifier("IMemberService")
    @Autowired
    private IMemberService memberService;

    @GetMapping("/category_ajx_list")
    // GET method로 /catajx/~~~~~~~~~ url 주소로 접속하도록 한다.
    public String category_ajx_list(@CookieValue(name="loginId", required = false) String loginId) {
        if ( loginId == null ) {
            return "redirect:/";
        }
        IMember loginUser = memberService.findByLoginId(loginId);
        if ( !loginUser.getRole().equals(MemberRole.ADMIN.toString()) ) {
            return "redirect:/";
        }
        return "catajx/category_ajx_list";
        // 화면 템플릿 엔진의 화면파일 경로/파일명
        // => resources/templates/catajx/category_ajx_list.html
    }
}
