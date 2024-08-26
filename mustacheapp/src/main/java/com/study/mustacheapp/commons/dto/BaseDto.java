package com.study.mustacheapp.commons.dto;

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
public class BaseDto implements IBase {
    private String createDt;
    private String createName;
    private Long createId;

    private String updateDt;
    private String updateName;
    private Long updateId;

    private String deleteDt;
    private String deleteName;
    private Long deleteId;

    private Boolean deleteFlag;
}
