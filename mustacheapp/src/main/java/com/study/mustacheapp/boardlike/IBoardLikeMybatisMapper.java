package com.study.mustacheapp.boardlike;

import com.study.mustacheapp.commons.inif.IMybatisCRUD;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IBoardLikeMybatisMapper extends IMybatisCRUD<BoardLikeDto> {
    void deleteByTableUserBoard(BoardLikeDto dto);
    Integer countByTableUserBoard(BoardLikeDto searchDto);
}
