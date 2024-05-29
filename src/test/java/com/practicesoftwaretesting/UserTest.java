package com.practicesoftwaretesting;

import com.practicesoftwaretesting.user.UserController;
import com.practicesoftwaretesting.user.model.LoginRequest;
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
                .assertStatusCode(201)
                .as();
        assertNotNull(user.getId());

        var userLoginResponse = userController.loginUser(new LoginRequest(userEmail, USER_PASSWORD))
                .assertStatusCode(200)
                .as();
        assertNotNull(userLoginResponse.getAccessToken());

        var adminLoginResponse = userController.loginUser(new LoginRequest(ADMIN_EMAIL, ADMIN_PASSWORD))
                .assertStatusCode(200)
                .as();

        var userId = user.getId();
        var token = adminLoginResponse.getAccessToken();
        userController.deleteUser(userId, token)
                .assertStatusCode(204);
    }
}





