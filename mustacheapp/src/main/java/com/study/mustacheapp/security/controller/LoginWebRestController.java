package com.study.mustacheapp.security.controller;

import com.study.mustacheapp.commons.dto.CUDInfoDto;
import com.study.mustacheapp.commons.dto.ResponseCode;
import com.study.mustacheapp.commons.dto.ResponseDto;
import com.study.mustacheapp.commons.inif.ICommonRestController;
import com.study.mustacheapp.member.IMember;
import com.study.mustacheapp.member.IMemberService;
import com.study.mustacheapp.security.config.SecurityConfig;
import com.study.mustacheapp.security.dto.LoginRequest;
import com.study.mustacheapp.security.dto.SignUpRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/v1/login")
public class LoginWebRestController implements ICommonRestController {
    @Autowired
    private IMemberService memberService;

    @PostMapping("/signup")
    private ResponseEntity<ResponseDto> signUp(Model model, @Valid @RequestBody SignUpRequest dto, BindingResult bindingResult) {
        try {
            if (dto == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(dto);
            IMember result = this.memberService.insert(cudInfoDto, dto);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }

    @PostMapping("/signin")
    private ResponseEntity<ResponseDto> signin(Model model, @RequestBody LoginRequest dto
        , HttpServletRequest request) {
        try {
            if (dto == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IMember loginUser = this.memberService.login(dto);
            if ( loginUser == null ) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R000074, "로그인 실패 실패 했습니다. ID와 암호를 확인하세요", null);
            }
            if ( !loginUser.getActive() ) {
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R000075, "회원계정이 비활성 상태입니다, 관리자에게 문의 하세요", null);
            }
            HttpSession session = request.getSession();
            session.setAttribute(SecurityConfig.LOGINUSER, loginUser.getNickname());
            session.setMaxInactiveInterval(60 * 60);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", true);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }

    @GetMapping("/signout")
    private ResponseEntity<ResponseDto> signout(HttpSession session) {
        session.invalidate();
        return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", true);
    }

    @Override
    public ResponseEntity<ResponseDto> insert(Model model, Object dto) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto> update(Model model, Long id, Object dto) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto> updateDeleteFlag(Model model, Long id, Object dto) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto> deleteById(Model model, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto> findById(Model model, Long id) {
        return null;
    }

//    @GetMapping("/signout")
//    private String signout(HttpSession session, HttpServletResponse response) {
//        Cookie cookie = new Cookie("loginId", null);
//        cookie.setMaxAge(0);
//        response.addCookie(cookie);
//        session.invalidate();
//        return "login/signout";
//    }
}
