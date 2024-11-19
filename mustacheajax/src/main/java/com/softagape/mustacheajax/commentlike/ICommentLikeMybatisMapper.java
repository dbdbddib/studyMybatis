package com.softagape.mustacheajax.commentlike;

import com.softagape.mustacheajax.commons.inif.IMybatisCRUD;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ICommentLikeMybatisMapper extends IMybatisCRUD<CommentLikeDto> {
    void deleteByCommentTableUserBoard(CommentLikeDto dto);
    Integer countByCommentTableUserBoard(CommentLikeDto searchDto);
}
