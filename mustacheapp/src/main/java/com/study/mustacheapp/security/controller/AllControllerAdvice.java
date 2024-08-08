package com.study.mustacheapp.security.controller;

import com.study.mustacheapp.member.IMember;
import com.study.mustacheapp.member.IMemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Arrays;

@ControllerAdvice   // 모든 URL 요청을 가로채서 처리한다.
public class AllControllerAdvice {
    @Qualifier("IMemberService")
    @Autowired
    private IMemberService memberService;

    private final String[] authUrls = new String[]{
            "/ct"
            , "/catajx"
            , "/catweb"
            , "/admin"
            , "/user"
    };


    @ModelAttribute // @ControllerAdvice, @ModelAttribute 이 단어가 있어야지만 모든 주소 요청시 가로챌수 있다.
    public void addModel( HttpServletRequest request, Model model
                          , @SessionAttribute(name = "loginId", required = false) String loginId ) {
        String url = request.getRequestURI();
        String bFind = Arrays.stream(this.authUrls)
                .filter(url::contains).findFirst().orElse(null);
        if ( bFind != null && loginId != null ) {
            IMember loginUser = this.memberService.findByLoginId(loginId);
            model.addAttribute("loginUser", loginUser);
        }
    }
}
