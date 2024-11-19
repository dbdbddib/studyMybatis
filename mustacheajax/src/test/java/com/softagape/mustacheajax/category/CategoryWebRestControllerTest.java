package com.softagape.mustacheajax.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softagape.mustacheajax.commons.dto.ResponseDto;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryWebRestControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private void AssertFields(ICategory left, ICategory right) {
        AssertionsForClassTypes.assertThat(left).isNotNull();
        AssertionsForClassTypes.assertThat(right).isNotNull();
        AssertionsForClassTypes.assertThat(left.getId()).isEqualTo(right.getId());
        AssertionsForClassTypes.assertThat(left.getName()).isEqualTo(right.getName());
    }

    private ResponseEntity<ResponseDto> postRestFul(String url, CategoryDto dto) {
        ResponseEntity<ResponseDto> response = this.testRestTemplate.postForEntity(
                url, dto, ResponseDto.class);
        assertThat(response).isNotNull();
        return response;
    }

    private CategoryDto mapperConvert(ResponseEntity<ResponseDto> dto) {
        assertThat(dto.getBody()).isNotNull();
        return this.mapperConvert(dto.getBody());
    }

    private CategoryDto mapperConvert(ResponseDto dto) {
        assertThat(dto).isNotNull();
        assertThat(dto.getResponseData()).isNotNull();
        ObjectMapper mapper = new ObjectMapper();
        CategoryDto result = mapper.convertValue(dto.getResponseData(), CategoryDto.class);
        return result;
    }

    private ResponseEntity<ResponseDto> getRestFul(String url, Long id) {
        ResponseEntity<ResponseDto> findEntity = this.testRestTemplate.getForEntity(
                url + "/" + id.toString(), ResponseDto.class
        ); // CategoryController 의 findById 가 실행된다.
        assertThat(findEntity).isNotNull();
        return findEntity;
    }

    private ResponseDto patchRestFul(String url, CategoryDto dto) {
        ResponseDto response = this.testRestTemplate.patchForObject(
                url + "/" + dto.getId(), dto, ResponseDto.class
        );
        assertThat(response).isNotNull();
        return response;
    }

    private void deleteRestFul(String url, Long id) {
        this.testRestTemplate.delete(url + "/" + id);
    }

    @Test
    public void CategoryTest() {
        String url = "http://localhost:" + port + "/api/vi/cat";
        CategoryDto requestInsert = CategoryDto.builder().build();
        ResponseEntity<ResponseDto> responseInsert = this.postRestFul(url, requestInsert);
        assertThat(responseInsert.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        CategoryDto requestInsert2 = CategoryDto.builder().name("RestFull").build();
        ResponseEntity<ResponseDto> responseInsert2 = this.postRestFul(url, requestInsert2);
        assertThat(responseInsert2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseInsert2.getBody()).isNotNull();
        assertThat(responseInsert2.getBody().getResponseData()).isNotNull();

        CategoryDto responseInsertCategory = this.mapperConvert(responseInsert2);
        System.out.println("responseInsert2.getBody().getId() = " + responseInsertCategory.getId());
        assertThat(responseInsertCategory.getName()).isEqualTo("RestFull");

        // Category Find Test
        Long insertId = responseInsertCategory.getId();
        ResponseEntity<ResponseDto> findEntity = this.getRestFul(url, insertId);
        assertThat(findEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        CategoryDto resultFind = this.mapperConvert(findEntity);

        assertThat(resultFind).isNotNull();
        assertThat(resultFind.getId()).isEqualTo(insertId);
        assertThat(resultFind.getName()).isEqualTo("RestFull");

        ResponseEntity<ResponseDto> notfindEntity = this.getRestFul(url, 99999999999L);
        assertThat(notfindEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        // Category Update Test
        CategoryDto update = CategoryDto.builder().build();
        update.copyFields(resultFind);
        update.setName("NoRest");
        ResponseDto resultObject = this.patchRestFul(url, update);
        CategoryDto resultUpdate = this.mapperConvert(resultObject);

        assertThat(resultUpdate.getName()).isEqualTo("NoRest");

        // Category Delete Test
        this.deleteRestFul(url, update.getId());

        ResponseEntity<ResponseDto> deleteEntity = this.getRestFul(url, update.getId());
        assertThat(deleteEntity).isNotNull();
        assertThat(deleteEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
