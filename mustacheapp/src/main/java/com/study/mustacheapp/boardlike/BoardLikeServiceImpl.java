package com.study.mustacheapp.boardlike;

import com.study.mustacheapp.commons.dto.CUDInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardLikeServiceImpl implements IBoardLikeService {
    @Autowired
    private IBoardLikeMybatisMapper boardLikeMybatisMapper;

    @Override
    public IBoardLike insert(CUDInfoDto info, IBoardLike dto) {
        if ( dto == null ) {
            return null;
        }
        BoardLikeDto insert = BoardLikeDto.builder().id(0L).build();
        insert.copyFields(dto);
        boardLikeMybatisMapper.insert(insert);
        return insert;
    }

    @Override
    public IBoardLike update(CUDInfoDto info, IBoardLike dto) {
        return null;
    }

    @Override
    public Boolean deleteFlag(CUDInfoDto info, IBoardLike dto) {
        return false;
    }

    @Override
    public Boolean deleteById(Long id) {
        return false;
    }

    @Override
    public IBoardLike findById(Long id) {
        return null;
    }

    @Override
    public Boolean deleteByTableUserBoard(BoardLikeDto dto) {
        if ( dto == null || dto.getTbl() == null || dto.getTbl().isEmpty()
                || dto.getLikeUserId() == null || dto.getLikeUserId().isEmpty()
                || dto.getBoardId() == null || dto.getBoardId() <= 0 ) {
            return false;
        }
        this.boardLikeMybatisMapper.deleteByTableUserBoard(dto);
        return true;
    }

    @Override
    public Integer countByTableUserBoard(IBoardLike searchDto) {
        if ( searchDto == null || searchDto.getTbl() == null || searchDto.getTbl().isEmpty()
                || searchDto.getLikeUserId() == null || searchDto.getLikeUserId().isEmpty()
                || searchDto.getBoardId() == null || searchDto.getBoardId() <= 0 ) {
            return 0;
        }
        BoardLikeDto search = BoardLikeDto.builder().build();
        search.copyFields(searchDto);
        Integer count = this.boardLikeMybatisMapper.countByTableUserBoard(search);
        return count;
    }
}
