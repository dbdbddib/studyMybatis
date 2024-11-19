package com.softagape.mustacheajax.stompevery;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IStompEveryChatMybatisMapper {
    void insert(StompEveryChatDto dto);
    List<StompEveryChatDto> findAllByRoomId(Long roomId);
}
