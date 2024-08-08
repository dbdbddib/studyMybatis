package com.study.mustacheapp.security.controller;

import com.study.mustacheapp.member.IMember;
import com.study.mustacheapp.member.IMemberService;
import com.study.mustacheapp.security.dto.LoginRequest;
import com.study.mustacheapp.security.dto.SignUpRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/selogin")
public class LoginSessionController {
    @Qualifier("IMemberService")
    @Autowired
    private IMemberService memberService;

    @GetMapping("/signup")
    private String viewSignUp() {
        return "login/signup";
    }

    @PostMapping("/signup")
    private String signUp(@ModelAttribute SignUpRequest dto) {
        try {
            if (dto == null) {
                return "redirect:/";
            }
            this.memberService.addMember(dto);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    private String viewLogin() {
        return "login/login";
    }

    @PostMapping("/signin")
    private String signin(Model model, @ModelAttribute LoginRequest dto
        , HttpServletRequest request) {
        try {
            if (dto == null) {
                return "redirect:/";
            }
            IMember loginUser = this.memberService.login(dto);
            if ( loginUser == null ) {
                return "login/fail";
            }
            HttpSession session = request.getSession();
            session.setAttribute("loginId", loginUser.getLoginId());
            session.setMaxInactiveInterval(60 * 60);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    private String logout(HttpServletResponse response) {
        // /logout 은 스프링 security 에서 처리하므로 이쪽 url 로 오지 않음
        return "login/signout";
    }

    @GetMapping("/signout")
    private String signout(HttpSession session) {
        session.invalidate();
        return "login/signout";
    }
}
