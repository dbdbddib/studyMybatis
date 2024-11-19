package com.study.mustacheapp.board;

import com.study.mustacheapp.commons.dto.CUDInfoDto;
import com.study.mustacheapp.commons.dto.SearchAjaxDto;
import com.study.mustacheapp.commons.inif.IServiceCRUD;
import com.study.mustacheapp.sbfile.SbFileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IBoardService extends IServiceCRUD<BoardDto> {
    void addViewQty(Long id);
    void addLikeQty(CUDInfoDto cudInfoDto, Long id);
    void subLikeQty(CUDInfoDto cudInfoDto, Long id);

    BoardDto insert(CUDInfoDto info, BoardDto dto, List<MultipartFile> files) throws RuntimeException;
    BoardDto update(CUDInfoDto info, BoardDto dto, List<SbFileDto> sbFileDtoList, List<MultipartFile> files) throws RuntimeException;
    Integer countAllByNameContains(SearchAjaxDto searchAjaxDto);
    List<BoardDto> findAllByNameContains(SearchAjaxDto searchAjaxDto);
}
