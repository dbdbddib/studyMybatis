package com.study.mustacheapp.security.controller;

import com.study.mustacheapp.member.IMember;
import com.study.mustacheapp.member.IMemberService;
import com.study.mustacheapp.member.MemberRole;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping("")
    private String index() {
        return "index";
    }

    @GetMapping("/signout")
    private String signout(HttpServletResponse response, HttpSession session) {
        session.invalidate();

        Cookie cookie = new Cookie("loginId", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "login/signout";
    }
}
