package com.study.mustacheapp.board;

import com.study.mustacheapp.filecntl.FileCtrlService;
import com.study.mustacheapp.sbfile.ISbFileMybatisMapper;
import com.study.mustacheapp.sbfile.ISbFileService;
import com.study.mustacheapp.sbfile.SbFileDto;
import com.study.mustacheapp.sblike.SbLikeDto;
import com.study.mustacheapp.sblike.ISbLikeMybatisMapper;
import com.study.mustacheapp.commons.dto.CUDInfoDto;
import com.study.mustacheapp.commons.dto.SearchAjaxDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements IBoardService {
    @Autowired
    private IBoardMybatisMapper boardMybatisMapper;

    @Autowired
    private ISbLikeMybatisMapper sbLikeMybatisMapper;

    @Autowired
    private ISbFileMybatisMapper sbFileMybatisMapper;

    @Autowired
    private FileCtrlService fileCtrlService;

    @Override
    public void addViewQty(Long id) {
        if ( id == null || id <= 0 ) {
            return;
        }
        this.boardMybatisMapper.addViewQty(id);
    }

    @Override
    public void addLikeQty(CUDInfoDto cudInfoDto, Long id) {
        if ( cudInfoDto == null || cudInfoDto.getLoginUser() == null || id == null || id <= 0 ) {
            return;
        }
        SbLikeDto boardLikeDto = SbLikeDto.builder()
                .tbl(new BoardDto().getTbl())
                .nickname(cudInfoDto.getLoginUser().getNickname())
                .boardId(id)
                .build();

        Integer count = this.sbLikeMybatisMapper.countByTableUserBoard(boardLikeDto);
        if ( count > 0 ) {
            return;
        }
        this.sbLikeMybatisMapper.insert(boardLikeDto);
        this.boardMybatisMapper.addLikeQty(id);
    }

    @Override
    public void subLikeQty(CUDInfoDto cudInfoDto, Long id) {
        if ( cudInfoDto == null || cudInfoDto.getLoginUser() == null || id == null || id <= 0 ) {
            return;
        }
        SbLikeDto boardLikeDto = SbLikeDto.builder()
                .tbl(new BoardDto().getTbl())
                .nickname(cudInfoDto.getLoginUser().getNickname())
                .boardId(id)
                .build();

        Integer count = this.sbLikeMybatisMapper.countByTableUserBoard(boardLikeDto);
        if ( count < 1 ) {
            return;
        }
        this.sbLikeMybatisMapper.deleteByTableUserBoard(boardLikeDto);
        this.boardMybatisMapper.subLikeQty(id);
    }

    @Override
    public Integer countAllByNameContains(SearchAjaxDto searchAjaxDto) {
        if ( searchAjaxDto == null ) {
            return 0;
        }
        Integer count = this.boardMybatisMapper.countAllByNameContains(searchAjaxDto);
        return count;
    }

    @Override
    public List<BoardDto> findAllByNameContains(SearchAjaxDto searchAjaxDto) {
        if ( searchAjaxDto == null ) {
            return List.of();
        }
        searchAjaxDto.setOrderByWord( (searchAjaxDto.getSortColumn() != null ? searchAjaxDto.getSortColumn() : "id")
                + " " + (searchAjaxDto.getSortAscDsc() != null ? searchAjaxDto.getSortAscDsc() : "DESC") );
        // SQL select 문장의 ORDER BY 구문을 만들어 주는 역할
        if ( searchAjaxDto.getRowsOnePage() == null ) {
            // 한 페이지당 보여주는 행의 갯수
            searchAjaxDto.setRowsOnePage(10);
        }
        if ( searchAjaxDto.getPage() <= 0 ) {
            searchAjaxDto.setPage(1);
        }
        List<BoardDto> list = this.boardMybatisMapper.findAllByNameContains(searchAjaxDto);
        return list;
    }

    private List<IBoard> getInterfaceList(List<BoardDto> list) {
        if( list == null ) {
            return List.of();
        }
        List<IBoard> result = list.stream().map(item -> (IBoard)item).toList();
        return result;
    }

    @Override
    public BoardDto insert(CUDInfoDto info, BoardDto dto) {
        if ( info == null || dto == null ) {
            return null;
        }
        BoardDto insert = BoardDto.builder().build();
        insert.copyFields(dto);
        info.setCreateInfo(insert);
        this.boardMybatisMapper.insert(insert);
        return insert;
    }

    @Override
    public BoardDto update(CUDInfoDto info, BoardDto dto) {
        if ( info == null || dto == null ) {
            return null;
        }
        BoardDto update = BoardDto.builder().build();
        update.copyFields(dto);
        info.setUpdateInfo(update);
        this.boardMybatisMapper.update(update);
        return update;
    }

    @Override
    public Boolean updateDeleteFlag(CUDInfoDto info, BoardDto dto) {
        if ( info == null || dto == null ) {
            return false;
        }
        BoardDto delete = BoardDto.builder().build();
        delete.copyFields(dto);
        info.setDeleteInfo(delete);
        this.boardMybatisMapper.updateDeleteFlag(delete);
        SbFileDto search = SbFileDto.builder().tbl(dto.getTbl()).boardId(delete.getId()).build();
        List<SbFileDto> list = this.sbFileMybatisMapper.findAllByTblBoardId(search);
        for ( SbFileDto sbFileDto : list ) {
            sbFileDto.setDeleteFlag(true);
            this.sbFileMybatisMapper.updateDeleteFlag(sbFileDto);
            // this.fileCtrlService.deleteFile(sbFileDto.getTbl(), sbFileDto.getUniqName(), sbFileDto.getFileType());
        }
        return true;
    }

    @Override
    public Boolean deleteById(Long id) {
        if ( id == null || id <= 0 ) {
            return false;
        }
        this.boardMybatisMapper.deleteById(id);
        return true;
    }

    @Override
    public BoardDto findById(Long id) {
        if ( id == null || id <= 0 ) {
            return null;
        }
        BoardDto find = this.boardMybatisMapper.findById(id);
        return find;
    }
}
