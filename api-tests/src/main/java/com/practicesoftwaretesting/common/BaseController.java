package com.practicesoftwaretesting.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.practicesoftwaretesting.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public abstract class BaseController<T> {

    private String authToken;

    private final ConfigReader configReader = new ConfigReader();

    static {
        configureRestAssured();
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .log(LogDetail.ALL)
                .build();
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }

    private static void configureRestAssured() {
        var objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        RestAssured.config = RestAssured.config()
                .objectMapperConfig(
                        RestAssured.config()
                                .getObjectMapperConfig()
                                .jackson2ObjectMapperFactory((cls, charset) -> objectMapper)
                );
    }

    public T withToken(String authToken) {
        this.authToken = authToken;
        return (T) this;
    }

    public void cleanToken() {
        this.authToken = null;
    }

    protected RequestSpecification baseClient() {
        RequestSpecification requestSpecification = RestAssured.given()
                .baseUri(configReader.getProperty("base.api.url"))
                .contentType(ContentType.JSON);
        if (authToken != null) {
            requestSpecification.header("Authorization", "Bearer" + authToken);
        }
        return requestSpecification;
    }
}
