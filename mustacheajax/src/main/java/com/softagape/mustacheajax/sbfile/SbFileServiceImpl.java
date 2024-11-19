package com.softagape.mustacheajax.sbfile;

import com.softagape.mustacheajax.board.IBoard;
import com.softagape.mustacheajax.commons.dto.CUDInfoDto;
import com.softagape.mustacheajax.commons.exeption.IdNotFoundException;
import com.softagape.mustacheajax.filecntl.FileCtrlService;
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
    public ISbFile insert(CUDInfoDto cudInfoDto, ISbFile dto) {
        if (dto == null) {
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
        if (id == null || id <= 0) {
            return false;
        }
        this.sbFileMybatisMapper.deleteById(id);
        return true;
    }

    @Override
    public ISbFile findById(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        SbFileDto find = this.sbFileMybatisMapper.findById(id);
        if (find == null) {
            throw new IdNotFoundException(String.format("Error : not found id = %d !", id));
        }
        return find;
    }

    @Override
    public List<ISbFile> findAllByTblBoardId(ISbFile search) {
        if (search == null) {
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
        try {
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
                this.sbFileMybatisMapper.insert(insert);
                this.fileCtrlService.saveFile(file, insert.getTbl(), insert.getUniqName() + insert.getFileType());
            }
            return true;
        } catch (Exception ex) {
            log.error(ex.toString());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Boolean updateFiles(List<SbFileDto> sbFileDtoList) {
        for ( SbFileDto sbFileDto : sbFileDtoList ) {
            if (sbFileDto.getDeleteFlag()) {
                this.sbFileMybatisMapper.updateDeleteFlag(sbFileDto);
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
