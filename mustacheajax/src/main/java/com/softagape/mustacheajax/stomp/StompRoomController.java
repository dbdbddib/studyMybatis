package com.softagape.mustacheajax.stomp;

import com.softagape.mustacheajax.commons.dto.CUDInfoDto;
import com.softagape.mustacheajax.commons.exeption.LoginAccessException;
import com.softagape.mustacheajax.commons.inif.IResponseController;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/stomp")
public class StompRoomController implements IResponseController {
    @Autowired
    private StompRoomService stompRoomService;

    @GetMapping("/list")
    public String stompRoomList(Model model) {
        try {
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            List<StompRoomDto> list = this.stompRoomService.findAll();
            model.addAttribute("stompRoomList", list);
            return "stomp/stomproomlist";
        } catch (LoginAccessException lae) {
            log.error(lae.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("/enter")
    public String enterStompRoom(Model model
            , HttpServletRequest request
            , @ModelAttribute StompRoomDto stompRoomDto
    ) {
        try {
            if (stompRoomDto == null) {
                return "redirect:/stomp/list";
            }
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            String writer = cudInfoDto.getLoginUser().getNickname();
            StompRoomDto stompRoom = this.stompRoomService.findByRoomId(stompRoomDto.getRoomId());
            if ( this.getIndexOfWriter(writer, stompRoom.getUserList()) >= 0 ) {
                log.error("{} 이미 존재하는 대화명", writer);
                return "redirect:/stomp/list";
            }
            model.addAttribute("stompRoomDto", stompRoom);
            model.addAttribute("writer", writer);
            String url = String.format("%s:%d", request.getServerName(), request.getServerPort());
            model.addAttribute("hostUrl", url);
            return "stomp/stomproomdetail";
        } catch (LoginAccessException lae) {
            log.error(lae.getMessage());
            return "redirect:/";
        }
    }

    private int getIndexOfWriter(String writer, List<String> userList) {
        for (int i = 0; i < userList.size(); i++) {
            String user = userList.get(i);
            if (user.equals(writer)) {
                return i;
            }
        }
        return -1;
    }
}
