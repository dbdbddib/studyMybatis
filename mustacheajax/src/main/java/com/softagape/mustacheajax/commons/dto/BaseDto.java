package com.softagape.mustacheajax.commons.dto;

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
    private Long createId;
    private String createName;
    private String updateDt;
    private Long updateId;
    private String updateName;
    private String deleteDt;
    private Long deleteId;
    private String deleteName;
    private Boolean deleteFlag;
}
