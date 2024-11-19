package com.softagape.mustacheajax.stompevery;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IStompEveryRoomMybatisMapper {
    void insert(StompEveryRoomDto dto);
    void update(StompEveryRoomDto dto);
    void deleteFlagById(Long id);
    StompEveryRoomDto findById(Long id);
    List<StompEveryRoomDto> findAll();
}
