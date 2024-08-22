package com.study.mustacheapp.sbfile;

import com.study.mustacheapp.board.BoardDto;
import com.study.mustacheapp.board.IBoard;
import com.study.mustacheapp.commons.dto.CUDInfoDto;
import com.study.mustacheapp.filecntl.FileCtrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class SbFileServiceImpl implements ISbFileService {
    @Autowired
    private ISbFileMybatisMapper sbFileMybatisMapper;

    @Autowired
    private FileCtrlService fileCtrlService;

    @Override
    public ISbFile insert(CUDInfoDto info, ISbFile dto) {
        if ( dto == null ) {
            return null;
        }
        SbFileDto insert = SbFileDto.builder().build();
        insert.copyFields(dto);
        this.sbFileMybatisMapper.insert(insert);
        return insert;
    }

    @Override
    public ISbFile update(CUDInfoDto info, ISbFile dto) {
        return null;
    }

    @Override
    public Boolean updateDeleteFlag(CUDInfoDto info, ISbFile dto) {
        if (dto == null || dto.getId() == null || dto.getId() <= 0) {
            return false;
        }
        SbFileDto update = SbFileDto.builder().build();
        update.copyFields(dto);
        this.sbFileMybatisMapper.updateDeleteFlag(update);
        return true;
    }

    @Override
    public Boolean deleteById(Long id) {
        if ( id == null || id <= 0 ) {
            return false;
        }
        this.sbFileMybatisMapper.deleteById(id);
        return true;
    }

    @Override
    public ISbFile findById(Long id) {
        if ( id == null || id <= 0 ) {
            return null;
        }
        SbFileDto find = this.sbFileMybatisMapper.findById(id);
        return find;
    }

    @Override
    public List<ISbFile> findAllByTblBoardId(ISbFile search) {
        if ( search == null ) {
            return List.of();
        }
        SbFileDto dto = SbFileDto.builder().build();
        dto.copyFields(search);
        List<SbFileDto> list = this.sbFileMybatisMapper.findAllByTblBoardId(dto);
        List<ISbFile> result = this.getInterfaceList(list);
        return result;
    }

    private List<ISbFile> getInterfaceList(List<SbFileDto> list) {
        if (list == null) {
            return List.of();
        }
        List<ISbFile> result = list.stream().map(x -> (ISbFile) x).toList();
        return result;
    }

    @Override
    public Boolean insertFiles(IBoard boardDto, List<MultipartFile> files) {
        if ( boardDto == null || files == null ) {
            return false;
        }
        int ord = 0;
        for ( MultipartFile file : files ) {
            SbFileDto insert = SbFileDto.builder()
                    .name(file.getOriginalFilename())
                    .ord(ord++)
                    .fileType(this.getFileType(Objects.requireNonNull(file.getOriginalFilename())))
                    .uniqName(UUID.randomUUID().toString())
                    .length(file.getSize())
                    .tbl(boardDto.getTbl())
                    .boardId(boardDto.getId())
                    .build();
            try {
                this.sbFileMybatisMapper.insert(insert);
                this.fileCtrlService.saveFile(file, insert.getTbl(), insert.getUniqName() + insert.getFileType());
            } catch (Exception ex) {
                log.error(ex.toString());
            }
        }
        return true;
    }

    @Override
    public Boolean updateFiles(List<SbFileDto> sbFileDtoList) {
        for ( ISbFile sbFileDto : sbFileDtoList ) {
            if (sbFileDto.getDeleteFlag()) {
                this.sbFileMybatisMapper.updateDeleteFlag((SbFileDto) sbFileDto);
            }
        }
        return true;
    }

    @Override
    public byte[] getBytesFromFile(ISbFile down) {
        if ( down == null ) {
            return new byte[0];
        }
        byte[] result = this.fileCtrlService.downloadFile(down.getTbl(), down.getUniqName(), down.getFileType());
        return result;
    }

    private String getFileType(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
