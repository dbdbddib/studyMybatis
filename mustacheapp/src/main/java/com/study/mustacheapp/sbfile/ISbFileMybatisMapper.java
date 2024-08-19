package com.study.mustacheapp.sbfile;

import com.study.mustacheapp.commons.inif.IMybatisCRUD;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ISbFileMybatisMapper extends IMybatisCRUD<SbFileDto> {
    List<SbFileDto> findAllByTblBoardId(SbFileDto search);
}
