package com.softagape.mustacheajax.security.controller;

import com.softagape.mustacheajax.member.IMember;
import com.softagape.mustacheajax.member.IMemberService;
import com.softagape.mustacheajax.security.config.SecurityConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Arrays;

@ControllerAdvice   // 모든 URL 요청을 가로채서 처리한다.
public class AllControllerAdvice {
    @Autowired
    private IMemberService memberService;

    private final String[] authUrls = new String[]{
            "/api"
            , "/catweb"
            , "/admin"
            , "/user"
            , "/member"
            , "/board"
            , "/stomp"
    };


    @ModelAttribute // @ControllerAdvice, @ModelAttribute 이 단어가 있어야지만 모든 주소 요청시 가로챌수 있다.
    public void addModel( HttpServletRequest request, Model model
                          , @SessionAttribute(name = SecurityConfig.LOGINUSER, required = false) String nickname ) {
        String url = request.getRequestURI();
        String bFind = Arrays.stream(this.authUrls)
                .filter(url::contains).findFirst().orElse(null);
        if ( bFind != null && nickname != null ) {
            IMember loginUser = this.memberService.findByNickname(nickname);
            model.addAttribute(SecurityConfig.LOGINUSER, loginUser);
        }
    }
}
