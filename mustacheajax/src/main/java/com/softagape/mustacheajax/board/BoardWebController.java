package com.softagape.mustacheajax.board;

import com.softagape.mustacheajax.member.IMember;
import com.softagape.mustacheajax.security.config.SecurityConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardWebController {

    @GetMapping("/board_ajx_list")
    private String boardAjxList(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
//            HttpCookie cookie,
//            HttpHeaders headers,
            HttpSession session
    ) {
        IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
        if ( loginUser == null ) {
            return "redirect:/";
        }
        model.addAttribute("boardTbl", new BoardDto().getTbl());
        return "board/board_ajx_list";
    }
}
