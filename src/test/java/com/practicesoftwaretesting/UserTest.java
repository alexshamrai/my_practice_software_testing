package com.practicesoftwaretesting;

import com.practicesoftwaretesting.user.UserController;
import com.practicesoftwaretesting.user.model.LoginRequest;
import com.practicesoftwaretesting.user.model.LoginResponse;
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
}





