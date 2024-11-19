package com.softagape.mustacheajax.stomp;

import com.softagape.mustacheajax.commons.dto.CUDInfoDto;
import com.softagape.mustacheajax.commons.exeption.LoginAccessException;
import com.softagape.mustacheajax.commons.inif.IResponseController;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/stomp")
public class StompRoomAjaxController implements IResponseController {
    @Autowired
    private StompRoomService stompRoomService;

    @PostMapping("/create")
    public ResponseEntity<StompRoomDto> createStompRoom(Model model, @RequestBody StompRoomDto stompRoomDto) {
        try {
            if (stompRoomDto == null) {
                return ResponseEntity.badRequest().build();
            }
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            StompRoomDto newRoom = this.stompRoomService.insert(stompRoomDto.getRoomName());
            return ResponseEntity.ok(newRoom);
        } catch (LoginAccessException lae) {
            log.error(lae.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

}
