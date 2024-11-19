package com.softagape.mustacheajax.category;

// 메소드 프로토타입만 선언하고 실행하는 몸체는 없는 인터페이스, 일반화, 추상화, 다형성, 객체지향의중심
public interface ICategory {
    Long getId();
    void setId(Long id);

    String getName();
    void setName(String name);

    // 인터페이스안에 몸체가 있는 메소드 이며, 마치 static 처럼 new 하지 않고 사용 된다.
    // 그러나 호출할때는 ICategory.copyfields 하면 안되고 객체명.copyfields 해야 한다.
    default void copyFields(ICategory from) {
        if (from == null) {
            return;
        }
        if (from.getId() != null) {
            this.setId(from.getId());
        }
        if (from.getName() != null && !from.getName().isEmpty()) {
            this.setName(from.getName());
        }
    }
}
