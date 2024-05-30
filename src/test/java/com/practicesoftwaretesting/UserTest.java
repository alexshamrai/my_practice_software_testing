package com.practicesoftwaretesting;

import com.practicesoftwaretesting.user.assertions.LoginResponseAsserts;
import com.practicesoftwaretesting.user.assertions.RegisterUserResponseAsserts;
import com.practicesoftwaretesting.user.UserController;
import com.practicesoftwaretesting.user.model.LoginRequest;
import org.junit.jupiter.api.Test;

public class UserTest extends BaseTest {

    private static final String USER_PASSWORD = "12Example#";
    private static final String ADMIN_EMAIL = "admin@practicesoftwaretesting.com";
    private static final String ADMIN_PASSWORD = "welcome01";

    @Test
    void testUser() {
        var userController = new UserController();
        var userEmail = getUserEmail();
        var expectedUser = buildUser(userEmail, USER_PASSWORD);
        var user = userController.registerUser(expectedUser)
                .assertStatusCode(201)
                .as();

        new RegisterUserResponseAsserts(user)
                .idIsNotNull()
                .firstNameIs(expectedUser.getFirstName())
                .lastNameIs(expectedUser.getLastName())
                .countryIs(expectedUser.getCountry())
                .postcodeIs(expectedUser.getPostcode())
                .emailIs(expectedUser.getEmail())
                .createdAtIsNotNull();

        var userLoginResponse = userController.loginUser(new LoginRequest(userEmail, USER_PASSWORD))
                .assertStatusCode(200)
                .as();
        new LoginResponseAsserts(userLoginResponse)
                .accessTokenIsNotNull()
                .tokenTypeIs("bearer")
                .isNotExpired();

        var adminLoginResponse = userController.loginUser(new LoginRequest(ADMIN_EMAIL, ADMIN_PASSWORD))
                .assertStatusCode(200)
                .as();
        new LoginResponseAsserts(adminLoginResponse)
                .accessTokenIsNotNull()
                .tokenTypeIs("bearer")
                .isNotExpired();

        var userId = user.getId();
        var token = adminLoginResponse.getAccessToken();
        userController.deleteUser(userId, token)
                .assertStatusCode(204);
    }
}





