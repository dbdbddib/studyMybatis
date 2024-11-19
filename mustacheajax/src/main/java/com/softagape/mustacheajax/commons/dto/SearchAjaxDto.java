package com.softagape.mustacheajax.commons.dto;

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

    public void settingValues() {
        this.setOrderByWord( (this.getSortColumn() != null ? this.getSortColumn() : "id")
                + " " + (this.getSortAscDsc() != null ? this.getSortAscDsc() : "DESC") );
        // SQL select 문장의 ORDER BY 구문을 만들어 주는 역할
        if ( this.getRowsOnePage() == null ) {
            // 한 페이지당 보여주는 행의 갯수
            this.setRowsOnePage(10);
        }
        if ( this.getPage() == null || this.getPage() <= 0 ) {
            this.setPage(1);
        }
    }
}
