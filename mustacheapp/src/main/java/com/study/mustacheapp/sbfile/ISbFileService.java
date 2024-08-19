package com.study.mustacheapp.sbfile;

import com.study.mustacheapp.board.BoardDto;
import com.study.mustacheapp.commons.inif.IServiceCRUD;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ISbFileService extends IServiceCRUD<ISbFile> {
    List<ISbFile> findAllByTblBoardId(ISbFile search);
    Boolean insertFiles(BoardDto boardDto, MultipartFile[] files);
    byte[] getBytesFromFile(ISbFile down);
}
