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
public class StompEveryRoomDto {
    private Long id;
    private String writer;
    private String roomName;
    private Boolean deleteFlag;
    private Integer count;
}
