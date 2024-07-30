package com.study.mustacheapp.category;

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
public class CategoryDto implements ICategory{
    private Long id;
    private String name;

    @Override
    public String toString() {
        return String.format("ID:%6d, 이름:%s}", this.id, this.name);
    }
}
