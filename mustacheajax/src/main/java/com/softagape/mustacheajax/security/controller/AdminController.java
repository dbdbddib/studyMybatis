package com.softagape.mustacheajax.security.controller;

import com.softagape.mustacheajax.member.IMember;
import com.softagape.mustacheajax.member.IMemberService;
import com.softagape.mustacheajax.member.MemberRole;
import com.softagape.mustacheajax.security.config.SecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {
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
        if ( !loginUser.getRole().equals(MemberRole.ADMIN.toString()) ) {
            return "redirect:/";
        }
        model.addAttribute(SecurityConfig.LOGINUSER, loginUser);
        return "admin/info";
    }

    @GetMapping("/infoSession")
    private String showInfoSession(Model model) {
        IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
        if ( loginUser == null ) {
            return "redirect:/";
        }
        if ( !loginUser.getRole().equals(MemberRole.ADMIN.toString()) ) {
            return "redirect:/";
        }
        return "admin/info";
    }
}
