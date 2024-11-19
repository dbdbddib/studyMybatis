package com.softagape.mustacheajax.category;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ICategoryMybatisMapperTest {
    @Autowired
    private ICategoryMybatisMapper categoryMybatisMapper;

    private void AssertFields(ICategory left, ICategory right) {
        assertThat(left).isNotNull();
        assertThat(right).isNotNull();
        assertThat(left.getId()).isEqualTo(right.getId());
        assertThat(left.getName()).isEqualTo(right.getName());
    }

    @Test
    @Order(1)
    public void CategoryInsertTest() {
        CategoryDto insert = CategoryDto.builder().build();
        Throwable exception = assertThrows(Exception.class, () -> {
            categoryMybatisMapper.insert(insert);
        });
        log.error("Exception : {}", exception.toString());

        CategoryDto insert2 = CategoryDto.builder()
                .name("1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890").build();
        exception = assertThrows(Exception.class, () -> {
            categoryMybatisMapper.insert(insert2);
        });
        log.error("Exception : {}", exception.toString());

        CategoryDto insert3 = CategoryDto.builder()
                .name("abcdef").build();
        this.categoryMybatisMapper.insert(insert3);
        assertThat(insert3).isNotNull();
        assertThat(insert3.getId()).isGreaterThan(0L);
        assertThat(insert3.getName()).isEqualTo("abcdef");
    }

    @Test
    @Order(2)
    public void CategoryFindTest() {
        CategoryDto find1 = this.categoryMybatisMapper.findById(0L);
        assertThat(find1).isNull();

        CategoryDto find2 = this.categoryMybatisMapper.findByName("abcdef");

        CategoryDto find3 = this.categoryMybatisMapper.findById(find2.getId());

        this.AssertFields(find3, find2);
    }

    @Test
    @Order(3)
    public void CategoryUpdateTest() {
        CategoryDto find2 = this.categoryMybatisMapper.findByName("abcdef");

        find2.setName("ABCDEFGH");
        this.categoryMybatisMapper.update(find2);

        CategoryDto find3 = this.categoryMybatisMapper.findById(find2.getId());

        this.AssertFields(find3, find2);
    }

    @Test
    @Order(4)
    public void CategoryDeleteTest() {
        CategoryDto find2 = this.categoryMybatisMapper.findByName("ABCDEFGH");
        assertThat(find2).isNotNull();

        this.categoryMybatisMapper.deleteById(find2.getId());

        CategoryDto find3 = this.categoryMybatisMapper.findById(find2.getId());
        assertThat(find3).isNull();
    }
}
