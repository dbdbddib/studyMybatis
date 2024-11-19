package com.softagape.mustacheajax.stompevery;

import com.softagape.mustacheajax.stomp.StompMessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StompEveryMessageDto {
    private StompMessageType msgType;
    private Long roomId;
    private String writer;
    private String msgTime;
    private String message;
}
