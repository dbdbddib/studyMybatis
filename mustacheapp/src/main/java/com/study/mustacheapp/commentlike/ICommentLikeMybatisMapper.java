package com.study.mustacheapp.commentlike;

import com.study.mustacheapp.commons.inif.IMybatisCRUD;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ICommentLikeMybatisMapper extends IMybatisCRUD<CommentLikeDto> {
    void deleteByCommentTableUserBoard(CommentLikeDto dto);
    Integer countByCommentTableUserBoard(CommentLikeDto searchDto);
}
