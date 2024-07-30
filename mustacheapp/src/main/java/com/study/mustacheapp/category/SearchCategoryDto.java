package com.study.mustacheapp.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class SearchCategoryDto extends CategoryDto {
    private String orderByWord;
    private int rowsOnePage;
    private int firstIndex;
    public int getFirstIndex() {
        return (this.page - 1) * this.rowsOnePage;
    }

    private int page;
    private int total;
}
