package com.softagape.mustacheajax.board;

import com.softagape.mustacheajax.commons.exeption.IdNotFoundException;
import com.softagape.mustacheajax.sbfile.ISbFileMybatisMapper;
import com.softagape.mustacheajax.sbfile.ISbFileService;
import com.softagape.mustacheajax.sbfile.SbFileDto;
import com.softagape.mustacheajax.sblike.SbLikeDto;
import com.softagape.mustacheajax.sblike.ISbLikeMybatisMapper;
import com.softagape.mustacheajax.commons.dto.CUDInfoDto;
import com.softagape.mustacheajax.commons.dto.SearchAjaxDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private ISbFileService sbFileService;

    @Override
    public void addViewQty(Long id) {
        if ( id == null || id <= 0 ) {
            return;
        }
        this.boardMybatisMapper.addViewQty(id);
    }

    @Override
    @Transactional
    public void addLikeQty(CUDInfoDto cudInfoDto, Long id) {
        if ( cudInfoDto == null || cudInfoDto.getLoginUser() == null || id == null || id <= 0 ) {
            return;
        }
        SbLikeDto boardLikeDto = SbLikeDto.builder()
                .tbl(new BoardDto().getTbl())
                .createId(cudInfoDto.getLoginUser().getId())
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
    @Transactional
    public void subLikeQty(CUDInfoDto cudInfoDto, Long id) {
        if ( cudInfoDto == null || cudInfoDto.getLoginUser() == null || id == null || id <= 0 ) {
            return;
        }
        SbLikeDto boardLikeDto = SbLikeDto.builder()
                .tbl(new BoardDto().getTbl())
                .createId(cudInfoDto.getLoginUser().getId())
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
        searchAjaxDto.settingValues();
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

    @Transactional
    public BoardDto insert(CUDInfoDto info, BoardDto dto, List<MultipartFile> files) throws RuntimeException {
        if ( info == null || dto == null ) {
            return null;
        }
        BoardDto insert = BoardDto.builder().build();
        insert.copyFields(dto);
        info.setCreateInfo(insert);
        this.boardMybatisMapper.insert(insert);
        this.sbFileService.insertFiles(insert, files);
        return insert;
    }

    @Transactional
    public BoardDto update(CUDInfoDto info, BoardDto dto
    , List<SbFileDto> sbFileDtoList, List<MultipartFile> files) throws RuntimeException {
        if ( info == null || dto == null ) {
            return null;
        }
        BoardDto update = BoardDto.builder().build();
        update.copyFields(dto);
        info.setUpdateInfo(update);
        this.boardMybatisMapper.update(update);
        this.sbFileService.updateFiles(sbFileDtoList);
        this.sbFileService.insertFiles(update, files);
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
        if ( find == null ) {
            throw new IdNotFoundException(String.format("Error : not found id = %d !", id));
        }
        return find;
    }

    @Override
    public BoardDto insert(CUDInfoDto cudInfoDto, BoardDto dto) {
        return null;
    }

    @Override
    public BoardDto update(CUDInfoDto cudInfoDto, BoardDto dto) {
        return null;
    }
}
