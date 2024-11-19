package com.softagape.mustacheajax.stomp;

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
public class StompMessageDto {
    private StompMessageType msgType;
    private String roomId;
    private String writer;
    private String message;
}
