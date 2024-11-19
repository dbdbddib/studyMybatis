package com.softagape.mustacheajax.sbfile;

import com.softagape.mustacheajax.board.IBoard;
import com.softagape.mustacheajax.commons.inif.IServiceCRUD;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ISbFileService extends IServiceCRUD<ISbFile> {
    List<ISbFile> findAllByTblBoardId(ISbFile search);
    Boolean insertFiles(IBoard boardDto, List<MultipartFile> files) throws RuntimeException;
    Boolean updateFiles(List<SbFileDto> sbFileDtoList);
    byte[] getBytesFromFile(ISbFile down);
}
