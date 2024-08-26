package com.study.mustacheapp.board.comment;

import com.study.mustacheapp.commentlike.CommentLikeDto;
import com.study.mustacheapp.commentlike.ICommentLikeMybatisMapper;
import com.study.mustacheapp.commons.dto.CUDInfoDto;
import com.study.mustacheapp.commons.dto.SearchAjaxDto;
import com.study.mustacheapp.member.IMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardCommentServiceImpl implements IBoardCommentService {
    @Autowired
    private IBoardCommentMybatisMapper boardCommentMybatisMapper;
    @Autowired
    private ICommentLikeMybatisMapper commentLikeMybatisMapper;

    @Override
    public void addLikeQty(CUDInfoDto cudInfoDto, Long id) {
        if ( cudInfoDto == null || cudInfoDto.getLoginUser() == null || id == null || id <= 0 ) {
            return;
        }
        CommentLikeDto commentLikeDto = CommentLikeDto.builder()
                .commentTbl(new BoardCommentDto().getTbl())
                .createId(cudInfoDto.getLoginUser().getId())
                .commentId(id)
                .build();

        Integer count = this.commentLikeMybatisMapper.countByCommentTableUserBoard(commentLikeDto);
        if ( count > 0 ) {
            return;
        }
        this.commentLikeMybatisMapper.insert(commentLikeDto);
        this.boardCommentMybatisMapper.addLikeQty(id);
    }

    @Override
    public void subLikeQty(CUDInfoDto cudInfoDto, Long id) {
        if ( cudInfoDto == null || cudInfoDto.getLoginUser() == null || id == null || id <= 0 ) {
            return;
        }
        CommentLikeDto commentLikeDto = CommentLikeDto.builder()
                .commentTbl(new BoardCommentDto().getTbl())
                .createId(cudInfoDto.getLoginUser().getId())
                .commentId(id)
                .build();

        Integer count = this.commentLikeMybatisMapper.countByCommentTableUserBoard(commentLikeDto);
        if ( count < 1 ) {
            return;
        }
        this.commentLikeMybatisMapper.deleteByCommentTableUserBoard(commentLikeDto);
        this.boardCommentMybatisMapper.subLikeQty(id);
    }

    @Override
    public Integer countAllByBoardId(SearchAjaxDto search) {
        if ( search == null ) {
            return 0;
        }
        Integer result = this.boardCommentMybatisMapper.countAllByBoardId(search);
        return result;
    }

    @Override
    public List<BoardCommentDto> findAllByBoardId(IMember loginUser, SearchBoardCommentDto dto) {
        if ( dto == null ) {
            return List.of();
        }
        dto.setOrderByWord( (dto.getSortColumn() != null ? dto.getSortColumn() : "id")
                + " " + (dto.getSortAscDsc() != null ? dto.getSortAscDsc() : "DESC") );
        if ( dto.getRowsOnePage() == null ) {
            dto.setRowsOnePage(10);
        }
        if ( dto.getPage() == null || dto.getPage() <= 0 ) {
            dto.setPage(1);
        }
        dto.setCommentTbl(new BoardCommentDto().getTbl());
        dto.setCreateId(loginUser.getId());
        List<BoardCommentDto> list = this.boardCommentMybatisMapper.findAllByBoardId(dto);
//        List<IBoardComment> result = this.getInterfaceList(list);
        return list;
    }

    private List<IBoardComment> getInterfaceList(List<BoardCommentDto> list) {
        if (list == null) {
            return List.of();
        }
        List<IBoardComment> result = list.stream().map(x -> (IBoardComment) x).toList();
        return result;
    }

    @Override
    public BoardCommentDto insert(CUDInfoDto info, BoardCommentDto dto) {
        if ( info == null || dto == null ) {
            return null;
        }
        BoardCommentDto insert = BoardCommentDto.builder().build();
        insert.copyFields(dto);
        info.setCreateInfo(insert);
        this.boardCommentMybatisMapper.insert(insert);
        return insert;
    }

    @Override
    public BoardCommentDto update(CUDInfoDto cudInfoDto, BoardCommentDto dto) {
        if ( cudInfoDto == null || dto == null ) {
            return null;
        }
        BoardCommentDto update = BoardCommentDto.builder().build();
        update.copyFields(dto);
        cudInfoDto.setUpdateInfo(update);
        this.boardCommentMybatisMapper.update(update);
        return update;
    }

    @Override
    public Boolean updateDeleteFlag(CUDInfoDto cudInfoDto, BoardCommentDto dto) {
        if ( cudInfoDto == null || dto == null ) {
            return false;
        }
        BoardCommentDto delete = BoardCommentDto.builder().build();
        delete.copyFields(dto);
        delete.setDeleteFlag(true);
        cudInfoDto.setDeleteInfo(delete);
        this.boardCommentMybatisMapper.updateDeleteFlag(delete);
        return true;
    }

    @Override
    public Boolean deleteById(Long id) {
        if ( id == null || id <= 0 ) {
            return false;
        }
        this.boardCommentMybatisMapper.deleteById(id);
        return true;
    }

    @Override
    public BoardCommentDto findById(Long id) {
        if ( id == null || id <= 0 ) {
            return null;
        }
        BoardCommentDto find = this.boardCommentMybatisMapper.findById(id);
        return find;
    }
}
