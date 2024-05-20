package com.practicesoftwaretesting;

import com.practicesoftwaretesting.user.model.LoginResponse;
import com.practicesoftwaretesting.user.model.RegisterUserResponse;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

public class UserTest {

    static {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://api.practicesoftwaretesting.com")
                .log(LogDetail.ALL)
                .build();
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }

    @Test
    void testBrands() {
        RestAssured.given()
                .get("/brands")
                .then()
                .statusCode(200);
    }

    @Test
    void testUser() {
        // Register user
        var requestBody = """
                {
                  "first_name": "John",
                  "last_name": "Lennon",
                  "address": "Street 1",
                  "city": "City",
                  "state": "State",
                  "country": "Country",
                  "postcode": "1234AA",
                  "phone": "0987654321",
                  "dob": "1941-01-01",
                  "password": "12Example#",
                  "email": "john@lennon.example"
                }
                """;

        var user = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/users/register")
                .as(RegisterUserResponse.class);

        // Login user
        var loginBody = """
                {
                  "email": "john@lennon.example",
                  "password": "12Example#"
                }
                """;

        var userLoginResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginBody)
                .post("/users/login")
                .as(LoginResponse.class);


        // Login admin
        var adminLoginBody = """
                {
                  "email": "admin@practicesoftwaretesting.com",
                  "password": "welcome01"
                }
                """;

        var adminLoginResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(adminLoginBody)
                .post("/users/login")
                .as(LoginResponse.class);

        // Delete user
        var userId = user.getId();
        var token = adminLoginResponse.getAccessToken();
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer" + token)
                .delete("users/" + userId)
                .then()
                .statusCode(204);
    }
}





