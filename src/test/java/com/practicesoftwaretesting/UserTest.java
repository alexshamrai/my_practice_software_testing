package com.practicesoftwaretesting;

import com.practicesoftwaretesting.user.assertions.LoginResponseAsserts;
import com.practicesoftwaretesting.user.assertions.RegisterUserResponseAsserts;
import com.practicesoftwaretesting.user.UserController;
import com.practicesoftwaretesting.user.model.LoginRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest extends BaseTest {

    private static final String USER_PASSWORD = "12Example#";

    UserController userController;
    String userId;

    @BeforeEach
    void setUp() {
        userController = new UserController();
    }

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
        userId = user.getId();

        var userLoginResponse = userController.loginUser(new LoginRequest(userEmail, USER_PASSWORD))
                .assertStatusCode(200)
                .as();
        new LoginResponseAsserts(userLoginResponse)
                .accessTokenIsNotNull()
                .tokenTypeIs("bearer")
                .isNotExpired();
    }

    @AfterEach
    void deleteUser() {
        var token = loginAsAdmin();
        userController.withToken(token).deleteUser(userId)
                .assertStatusCode(204);
    }
}





