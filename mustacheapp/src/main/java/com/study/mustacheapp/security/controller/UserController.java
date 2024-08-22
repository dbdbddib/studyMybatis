package com.study.mustacheapp.security.controller;

import com.study.mustacheapp.member.IMember;
import com.study.mustacheapp.member.IMemberService;
import com.study.mustacheapp.security.config.SecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    @Qualifier("memberServiceImpl")
    @Autowired
    private IMemberService memberService;

    @GetMapping("/infoCookie")
    private String showInfoCookie(Model model, @CookieValue(value = SecurityConfig.LOGINUSER, required = false) String loginKeyName) {
        if ( loginKeyName == null ) {
            return "redirect:/";
        }
        IMember loginUser = this.memberService.findByNickname(loginKeyName);
        if ( loginUser == null ) {
            return "redirect:/";
        }
        model.addAttribute(SecurityConfig.LOGINUSER, loginUser);
        return "user/info";
    }

    @GetMapping("/infoSession")
    private String showInfoSession(Model model) {
        IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
        if ( loginUser == null ) {
            return "redirect:/";
        }
        return "user/info";
    }
}
