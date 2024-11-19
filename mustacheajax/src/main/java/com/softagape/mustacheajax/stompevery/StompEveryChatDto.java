package com.softagape.mustacheajax.stompevery;

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
public class StompEveryChatDto extends StompEveryMessageDto {
    private Long id;
    private String roomName;
}
