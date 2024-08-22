package com.study.mustacheapp.commentlike;

import com.study.mustacheapp.commons.dto.CUDInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeServiceImpl implements ICommentLikeService {
    @Autowired
    private ICommentLikeMybatisMapper commentLikeMybatisMapper;

    @Override
    public ICommentLike insert(CUDInfoDto info, ICommentLike dto) {
        if ( dto == null ) {
            return null;
        }
        CommentLikeDto insert = CommentLikeDto.builder().id(0L).build();
        insert.copyFields(dto);
        commentLikeMybatisMapper.insert(insert);
        return insert;
    }

    @Override
    public ICommentLike update(CUDInfoDto info, ICommentLike dto) {
        return null;
    }

    @Override
    public Boolean updateDeleteFlag(CUDInfoDto info, ICommentLike dto) {
        return false;
    }

    @Override
    public Boolean deleteById(Long id) {
        return false;
    }

    @Override
    public ICommentLike findById(Long id) {
        return null;
    }

    @Override
    public Boolean deleteByCommentTableUserBoard(CommentLikeDto dto) {
        if ( dto == null || dto.getCommentTbl() == null || dto.getCommentTbl().isEmpty()
                || dto.getNickname() == null || dto.getNickname().isEmpty()
                || dto.getCommentId() == null || dto.getCommentId() <= 0 ) {
            return false;
        }
        this.commentLikeMybatisMapper.countByCommentTableUserBoard(dto);
        return true;
    }

    @Override
    public Integer countByCommentTableUserBoard(ICommentLike searchDto) {
        if ( searchDto == null || searchDto.getCommentTbl() == null || searchDto.getCommentTbl().isEmpty()
                || searchDto.getNickname() == null || searchDto.getNickname().isEmpty()
                || searchDto.getCommentId() == null || searchDto.getCommentId() <= 0 ) {
            return 0;
        }
        CommentLikeDto search = CommentLikeDto.builder().build();
        search.copyFields(searchDto);
        Integer count = this.commentLikeMybatisMapper.countByCommentTableUserBoard(search);
        return count;
    }
}
