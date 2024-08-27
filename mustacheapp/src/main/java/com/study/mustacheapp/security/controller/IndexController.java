package com.study.mustacheapp.security.controller;

import com.study.mustacheapp.member.IMember;
import com.study.mustacheapp.member.IMemberService;
import com.study.mustacheapp.member.MemberRole;
import com.study.mustacheapp.security.config.SecurityConfig;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    private IMemberService memberService;

    @GetMapping("")
    private String index(Model model, @SessionAttribute(name = SecurityConfig.LOGINUSER, required = false) String nickname) {
        if ( nickname != null ) {
            IMember loginUser = this.memberService.findByNickname(nickname);
            model.addAttribute(SecurityConfig.LOGINUSER, loginUser);
        }
        return "index";
    }

    @GetMapping("/signout")
    private String signout(HttpServletResponse response, HttpSession session) {
        session.invalidate();

        Cookie cookie = new Cookie(SecurityConfig.LOGINUSER, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "login/signout";
    }
}
