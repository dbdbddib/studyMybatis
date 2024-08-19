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
    private String createId;
    @Null
    private String updateDt;
    @Null
    private String updateId;
    @Null
    private String deleteDt;
    @Null
    private String deleteId;
    @Null
    private Boolean deleteFlag;
}
