package com.practicesoftwaretesting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.github.javafaker.Faker;
import com.practicesoftwaretesting.user.model.LoginRequest;
import com.practicesoftwaretesting.user.model.LoginResponse;
import com.practicesoftwaretesting.user.model.RegisterUserRequest;
import com.practicesoftwaretesting.user.model.RegisterUserResponse;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTest {

    private static final String USER_PASSWORD = "12Example#";
    private static final String ADMIN_EMAIL = "admin@practicesoftwaretesting.com";
    private static final String ADMIN_PASSWORD = "welcome01";

    static {
        configureRestAssured();
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://api.practicesoftwaretesting.com")
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

    @Test
    void testUser() {
        var userEmail = getUserEmail();
        var registerUserRequest = buildUser(userEmail, USER_PASSWORD);
        var user = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(registerUserRequest)
                .post("/users/register")
                .as(RegisterUserResponse.class);
        assertNotNull(user.getId());

        var userLoginResponse = loginUser(userEmail, USER_PASSWORD);
        assertNotNull(userLoginResponse.getAccessToken());

        var adminLoginResponse = loginUser(ADMIN_EMAIL, ADMIN_PASSWORD);

        var userId = user.getId();
        var token = adminLoginResponse.getAccessToken();
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer" + token)
                .delete("users/" + userId)
                .then()
                .statusCode(204);
    }

    private String getUserEmail() {
        return Faker.instance()
                .friends()
                .character()
                .toLowerCase()
                .replaceAll(" ", "") + "@gmail.com";
    }

    private static LoginResponse loginUser(String email, String password) {
        var loginRequest = new LoginRequest(email, password);
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .post("/users/login")
                .as(LoginResponse.class);
    }

    private RegisterUserRequest buildUser(String email, String password) {
        return RegisterUserRequest.builder()
                .firstName("John")
                .lastName("Lennon")
                .address("Street 1")
                .city("City")
                .state("State")
                .country("Country")
                .postcode("1234AA")
                .phone("0987654321")
                .dob("1941-01-01")
                .email(email)
                .password(password)
                .build();
    }
}





