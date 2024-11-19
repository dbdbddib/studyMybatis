package com.softagape.mustacheajax;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestReadOpenApi {

    @Test
    public void TestRead01() throws Exception {
        String url = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1?numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=TestApp&_type=json&listYN=Y&arrange=C&serviceKey=ZK5%2FABP5iNBCAOoQYO1NPKX7ml5Iv4yQs5jo8bokCDw5RvV%2BwogquKHDfUj58azCWXGgn36NF9%2FqqYzoJI7ovA%3D%3D";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response).isNotNull();
        System.out.println(response.body().toString());
    }
}
