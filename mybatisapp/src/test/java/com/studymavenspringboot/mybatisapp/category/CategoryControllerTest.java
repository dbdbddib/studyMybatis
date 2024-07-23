package com.studymavenspringboot.mybatisapp.category;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void CategoryTest() {
        String url = "http://localhost:" + port;
        CategoryDto requestInsert = CategoryDto.builder().build();
        // post니깐 insert가 실행
        ResponseEntity<CategoryDto> responstInsert = this.testRestTemplate.postForEntity(url +
                "/ct", requestInsert, CategoryDto.class);
        assertThat(responstInsert).isNotNull();
        assertThat(responstInsert.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        CategoryDto requestInsert2 = CategoryDto.builder().name("RestFullAPI").build();
        ResponseEntity<CategoryDto> responstInsert2 = this.testRestTemplate.postForEntity(url +
                "/ct", requestInsert2, CategoryDto.class);
        assertThat(responstInsert2).isNotNull();
        assertThat(responstInsert2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responstInsert2.getBody()).isNotNull();
        assertThat(responstInsert2.getBody().getName()).isEqualTo("RestFullAPI");


        // Category Find Test
        Long insertId = responstInsert2.getBody().getId();
        ResponseEntity<CategoryDto> findEntity = this.testRestTemplate.getForEntity(
                url + "/ct/" + insertId.toString()
                , CategoryDto.class
        );  // Controller 의 findById가 실행된다
        assertThat(findEntity).isNotNull();
        assertThat(findEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        ICategory resultFind = findEntity.getBody();
        assertThat(resultFind).isNotNull();
        assertThat(resultFind.getId()).isEqualTo(insertId);
        assertThat(resultFind.getName()).isEqualTo("RestFullAPI");


        ResponseEntity<CategoryDto> notfindEntity = this.testRestTemplate.getForEntity(
                url + "/ct/878768786"   // 0 집어 넣으면 NOT_FOUND 실행안됨
                , CategoryDto.class
        );
        assertThat(notfindEntity).isNotNull();
        assertThat(notfindEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);


        // Update Test
        ICategory update = CategoryDto.builder().build();
        update.copyFields(resultFind);
        update.setName("NoRest");
        CategoryDto resultObject = this.testRestTemplate.patchForObject(
                url + "/ct/" + update.getId()
                , update
                , CategoryDto.class
        );
        assertThat(resultObject).isNotNull();
        assertThat(resultObject.getName()).isEqualTo("NoRest");


        // Category Delete Test
        ICategory delete = CategoryDto.builder().id(update.getId()).build();
        this.testRestTemplate.delete(url + "/ct/" + delete.getId());

        ResponseEntity<CategoryDto> deleteEntity = this.testRestTemplate.getForEntity(
                url + "/ct/9999999" + delete.getId()
                , CategoryDto.class
        );
        assertThat(deleteEntity).isNotNull();
        assertThat(deleteEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
