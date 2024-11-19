package com.softagape.mustacheajax.stomp;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StompRoomService {
    private final Map<String, StompRoomDto> stompRoomDtoMap = new LinkedHashMap<>();

    public StompRoomDto insert(String roomName) {
        StompRoomDto newRoom = StompRoomDto.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(roomName)
                .userList(new ArrayList<>())
                .build();
        stompRoomDtoMap.put(newRoom.getRoomId(), newRoom);
        return newRoom;
    }

    public StompRoomDto findByRoomId(String roomId) {
        return stompRoomDtoMap.get(roomId);
    }

    public List<StompRoomDto> findAll() {
        return stompRoomDtoMap.values().stream().toList();
    }

    public void deleteByRoomId(String roomId) {
        stompRoomDtoMap.remove(roomId);
    }
}
