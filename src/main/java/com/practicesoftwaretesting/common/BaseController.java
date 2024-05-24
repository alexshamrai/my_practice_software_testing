package com.practicesoftwaretesting.common;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public abstract class BaseController<T> {

    private String authToken;

    public static final String BASE_URI = "https://api.practicesoftwaretesting.com";

    public T withToken(String authToken) {
        this.authToken = authToken;
        return (T) this;
    }

    public void cleanToken() {
        this.authToken = null;
    }

    protected RequestSpecification baseClient() {
        RequestSpecification requestSpecification = RestAssured.given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON);
        if (authToken != null) {
            requestSpecification.header("Authorization", "Bearer" + authToken);
        }
        return requestSpecification;
    }
}
