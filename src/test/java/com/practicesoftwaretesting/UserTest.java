package com.practicesoftwaretesting;

import com.github.javafaker.Faker;
import com.practicesoftwaretesting.user.UserController;
import com.practicesoftwaretesting.user.model.LoginRequest;
import com.practicesoftwaretesting.user.model.LoginResponse;
import com.practicesoftwaretesting.user.model.RegisterUserRequest;
import com.practicesoftwaretesting.user.model.RegisterUserResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTest extends BaseTest {

    private static final String USER_PASSWORD = "12Example#";
    private static final String ADMIN_EMAIL = "admin@practicesoftwaretesting.com";
    private static final String ADMIN_PASSWORD = "welcome01";

    @Test
    void testUser() {
        var userController = new UserController();
        var userEmail = getUserEmail();
        var registerUserRequest = buildUser(userEmail, USER_PASSWORD);
        var user = userController.registerUser(registerUserRequest)
                .as(RegisterUserResponse.class);
        assertNotNull(user.getId());

        var userLoginResponse = userController.loginUser(new LoginRequest(userEmail, USER_PASSWORD))
                .as(LoginResponse.class);
        assertNotNull(userLoginResponse.getAccessToken());

        var adminLoginResponse = userController.loginUser(new LoginRequest(ADMIN_EMAIL, ADMIN_PASSWORD))
                .as(LoginResponse.class);

        var userId = user.getId();
        var token = adminLoginResponse.getAccessToken();
        userController.deleteUser(userId, token)
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





