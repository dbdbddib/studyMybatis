package com.study.mustacheapp.commons.dto;

import jakarta.validation.constraints.Null;
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
public class BaseNullRequest implements IBase {
    @Null
    private String createDt;
    @Null
    private Long createId;
    @Null
    private String updateDt;
    @Null
    private Long updateId;
    @Null
    private String deleteDt;
    @Null
    private Long deleteId;
    @Null
    private Boolean deleteFlag;
}
