package com.softagape.mustacheajax.stompevery;

import com.softagape.mustacheajax.stomp.StompMessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Controller
public class StompEveryChatController {
    @Autowired
    private SimpMessageSendingOperations msgTempate;

    @Autowired
    private StompEveryRoomService stompEveryRoomService;

    @Autowired
    private StompEveryChatService stompEveryChatService;

    @MessageMapping("/stompevery/message")
    public void message(StompEveryMessageDto messageDto) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messageDto.setMsgTime(simpleDateFormat.format(new Date()));
        log.info("/stompevery/message => roomId:{}, msgType:{}, msgTime:{}, writer:{}, message:{}"
                , messageDto.getRoomId()
                , messageDto.getMsgType()
                , messageDto.getMsgTime()
                , messageDto.getWriter()
                , messageDto.getMessage()
        );
        StompEveryRoomDto stompRoom = stompEveryRoomService.findByRoomId(messageDto.getRoomId());
        if (stompRoom == null) {
            return;
        }
        StompEveryChatDto chatDto = StompEveryChatDto.builder()
                .id(0L).roomId(messageDto.getRoomId())
                .writer(messageDto.getWriter())
                .msgTime(messageDto.getMsgTime())
                .message(messageDto.getMessage())
                .build();
        if ( StompMessageType.ENTER == messageDto.getMsgType() ) {
//            stompRoom.getUserList().add(StompEveryMessageDto.getWriter());
            stompRoom.setCount(stompRoom.getCount() + 1);
            stompEveryRoomService.update(stompRoom);
        } else if ( StompMessageType.OUT == messageDto.getMsgType() ) {
//            stompRoom.getUserList().remove(StompEveryMessageDto.getWriter());
            stompRoom.setCount(stompRoom.getCount() - 1);
            stompEveryRoomService.update(stompRoom);
        }
        if( stompRoom.getCount() < 1 ) {
            stompEveryRoomService.deleteByRoomId(messageDto.getRoomId(), chatDto);
        } else {
            msgTempate.convertAndSend("/sub/stompevery/room/" + messageDto.getRoomId()
                    , messageDto);
            this.stompEveryChatService.insert(chatDto);
        }
    }
}
