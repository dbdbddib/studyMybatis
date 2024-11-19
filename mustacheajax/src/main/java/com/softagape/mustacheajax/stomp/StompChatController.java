package com.softagape.mustacheajax.stomp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class StompChatController {
    @Autowired
    private SimpMessageSendingOperations msgTempate;

    @Autowired
    private StompRoomService stompRoomService;

    @MessageMapping("/stomp/message")
    public void message(StompMessageDto stompMessageDto) {
        log.info("/stomp/message => roomId:{}, msgType:{}, writer:{}, message:{}"
                , stompMessageDto.getRoomId()
                , stompMessageDto.getMsgType()
                , stompMessageDto.getWriter()
                , stompMessageDto.getMessage()
        );
        StompRoomDto stompRoom = stompRoomService.findByRoomId(stompMessageDto.getRoomId());
        if (stompRoom == null) {
            return;
        }
        if ( StompMessageType.ENTER == stompMessageDto.getMsgType() ) {
            stompRoom.getUserList().add(stompMessageDto.getWriter());
        } else if ( StompMessageType.OUT == stompMessageDto.getMsgType() ) {
            stompRoom.getUserList().remove(stompMessageDto.getWriter());
        }
        if( stompRoom.getCount() < 1 ) {
            stompRoomService.deleteByRoomId(stompMessageDto.getRoomId());
        } else {
            msgTempate.convertAndSend("/sub/stomp/room/" + stompMessageDto.getRoomId()
                    , stompMessageDto);
        }
    }
}
