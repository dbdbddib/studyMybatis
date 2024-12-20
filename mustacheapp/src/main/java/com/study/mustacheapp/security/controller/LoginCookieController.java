package com.study.mustacheapp.security.controller;

import com.study.mustacheapp.commons.dto.CUDInfoDto;
import com.study.mustacheapp.member.IMember;
import com.study.mustacheapp.member.IMemberService;
import com.study.mustacheapp.member.MemberServiceImpl;
import com.study.mustacheapp.security.config.SecurityConfig;
import com.study.mustacheapp.security.dto.LoginRequest;
import com.study.mustacheapp.security.dto.SignUpRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/cologin")
public class LoginCookieController {
    @Autowired
    private IMemberService memberService;

    @GetMapping("/signup")
    private String viewSignUp() {
        return "login/signup";
    }

    @PostMapping("/signup")
    private String signUp(Model model, @Valid @ModelAttribute SignUpRequest dto, BindingResult bindingResult) {
        try {
            if (dto == null) {
                return "redirect:/";
            }
            if (bindingResult.hasErrors()) {
                List<String> errorList = new ArrayList<>();
                for (FieldError error : bindingResult.getFieldErrors()) {
                    errorList.add(error.getField() + " : " + error.getDefaultMessage());
                    log.info(error.getDefaultMessage());
                }
                model.addAttribute("errorList", errorList);
                return "login/fail";
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(dto);
            IMember iMember = this.memberService.insert(cudInfoDto, dto);
        } catch (Exception ex) {
            log.error(ex.toString());
            model.addAttribute("message", "회원 가입 실패 했습니다. 입력 정보를 다시 확인하거나 관리자에게 문의하세요");
            return "login/fail";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    private String viewLogin() {
        return "login/login";
    }

    @PostMapping("/signin")
    private String signin(Model model, @ModelAttribute LoginRequest dto
        , HttpServletResponse response) {
        try {
            if (dto == null) {
                return "redirect:/";
            }
            IMember loginUser = this.memberService.login(dto);
            if ( loginUser == null ) {
                model.addAttribute("message", "로그인 실패 실패 했습니다. ID와 암호를 확인하세요");
                return "login/fail";
            } else if ( !loginUser.getActive() ) {
                model.addAttribute("message", "회원계정이 비활성 상태입니다, 관리자에게 문의 하세요");
                return "login/fail";
            }
            Cookie cookie = new Cookie(SecurityConfig.LOGINUSER, loginUser.getNickname());
            cookie.setMaxAge(60 * 30);
            cookie.setPath("/");    // 쿠키 사용 가능한 url 주소를 root 로 설정
            cookie.setHttpOnly(true);   // 쿠키를 client 에서 수정 못하도록 설정
            response.addCookie(cookie);
        } catch (Exception ex) {
            log.error(ex.toString());
            model.addAttribute("message", "로그인 실패 실패 했습니다. 관리자에게 문의 하세요");
            return "login/fail";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    private String logout(HttpServletResponse response) {
        // /logout 은 스프링 security 에서 처리하므로 이쪽 url 로 오지 않음
        return "login/signout";
    }
}
