package com.softagape.mustacheajax.member;

import com.softagape.mustacheajax.security.config.SecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/member")
public class MemberWebController {
    @Autowired
    private IMemberService memberService;

    @GetMapping("member_ajx_list")
    public String member_ajx_list(Model model) {
        IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
        if (loginUser == null) {
            return "redirect:/";
        }
        if ( loginUser.getRole().equals(MemberRole.ADMIN.toString()) ) {
            model.addAttribute("adminUser", true);
        }
        return "member/member_ajx_list";
    }
}
