package com.softagape.mustacheajax.stomp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StompRoomDto {
    private String roomName;
    private String roomId;
    private List<String> userList;
    public Integer getCount() {
        return userList.size();
    };
}
