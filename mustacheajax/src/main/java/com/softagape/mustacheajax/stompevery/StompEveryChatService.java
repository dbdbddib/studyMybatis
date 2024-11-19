package com.softagape.mustacheajax.stompevery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StompEveryChatService {
    @Autowired
    private IStompEveryChatMybatisMapper iStompEveryChatMybatisMapper;

    public void insert(StompEveryChatDto dto) {
        iStompEveryChatMybatisMapper.insert(dto);
    }

    public List<StompEveryChatDto> findAllByRoomId(Long roomId) {
        return iStompEveryChatMybatisMapper.findAllByRoomId(roomId);
    }
}
