package com.study.mustacheapp.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SearchAjaxDto {
    private String orderByWord;
    private String searchName;
    private String sortColumn;
    private String sortAscDsc;
    private Integer rowsOnePage;
    private Integer firstIndex;
    public Integer getFirstIndex() {
        return (this.page - 1) * this.rowsOnePage;
    }

    private Integer page;
    private Integer total;
    private List<?> dataList;
}
