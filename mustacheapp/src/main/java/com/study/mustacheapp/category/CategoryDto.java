package com.study.mustacheapp.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder           //@Biulder 는 상속 x, @SuperBuilder 는 상속 가능
@NoArgsConstructor      // 매개변수 없는 기본생성자 자동 생성
@AllArgsConstructor     // 모든 매개변수 생성자 자동 생성


// 데이터를 전송하기 위한 클래스  (
public class CategoryDto implements ICategory{
    private Long id;
    private String name;

    @Override
    public String toString() {
        return String.format("ID:%6d, 이름:%s}", this.id, this.name);
    }
}
