package com.softagape.mustacheajax.stompevery;

import com.softagape.mustacheajax.commons.exeption.LoginAccessException;
import com.softagape.mustacheajax.commons.inif.IResponseController;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/stompevery")
public class StompEveryRoomController implements IResponseController {
    @Autowired
    private StompEveryRoomService stompEveryRoomService;

    @GetMapping("/list")
    public String stompRoomList(Model model) {
        try {
//            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            List<StompEveryRoomDto> list = this.stompEveryRoomService.findAll();
            model.addAttribute("stompRoomList", list);
            return "stompevery/stompeveryroomlist";
        } catch (LoginAccessException lae) {
            log.error(lae.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("/enter")
    public String enterStompRoom(Model model
            , HttpServletRequest request
            , @ModelAttribute StompEveryRoomDto stompEveryRoomDto
    ) {
        try {
            if (stompEveryRoomDto == null) {
                return "redirect:/stompevery/list";
            }
//            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            String writer = stompEveryRoomDto.getWriter();
            StompEveryRoomDto stompRoom = this.stompEveryRoomService.findByRoomId(stompEveryRoomDto.getId());
//            StompEveryRoomDto stompRoom = this.stompRoomEveryService.findByRoomId(stompEveryRoomDto.getRoomId());
//            if ( this.getIndexOfWriter(writer, stompRoom.getUserList()) >= 0 ) {
//                log.error("{} 이미 존재하는 대화명", writer);
//                return "redirect:/stomp/list";
//            }
            model.addAttribute("stompEveryRoomDto", stompRoom);
            model.addAttribute("writer", writer);
            String url = String.format("%s:%d", request.getServerName(), request.getServerPort());
            model.addAttribute("hostUrl", url);
            return "stompevery/stompeveryroomdetail";
        } catch (LoginAccessException lae) {
            log.error(lae.getMessage());
            return "redirect:/";
        }
    }
}
