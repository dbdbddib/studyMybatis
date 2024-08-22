package com.study.mustacheapp.sbfile;

import com.study.mustacheapp.board.BoardDto;
import com.study.mustacheapp.board.IBoard;
import com.study.mustacheapp.commons.dto.CUDInfoDto;
import com.study.mustacheapp.commons.dto.SearchAjaxDto;
import com.study.mustacheapp.commons.inif.IServiceCRUD;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ISbFileService extends IServiceCRUD<ISbFile> {
    List<ISbFile> findAllByTblBoardId(ISbFile search);
    Boolean insertFiles(IBoard boardDto, List<MultipartFile> files);
    Boolean updateFiles(List<SbFileDto> sbFileDtoList);
    byte[] getBytesFromFile(ISbFile down);
}
