package com.study.mustacheapp.sbfile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SbFileDto implements ISbFile {
    private Long id;
    private String name;
    private Integer ord;
    private String fileType;
    private String uniqName;
    private Integer length;
    private String description;
    private String tbl;
    private Long boardId;
}
