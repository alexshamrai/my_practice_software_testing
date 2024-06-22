package com.practicesoftwaretesting;

import com.practicesoftwaretesting.user.assertions.LoginResponseAsserts;
import com.practicesoftwaretesting.user.assertions.RegisterUserResponseAsserts;
import com.practicesoftwaretesting.user.UserController;
import com.practicesoftwaretesting.user.model.LoginRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.practicesoftwaretesting.user.UserSteps.buildUser;
import static com.practicesoftwaretesting.user.UserSteps.generateUserEmail;

public class UserTest extends BaseTest {

    UserController userController;
    String userId;

    @BeforeEach
    void setUp() {
        userController = new UserController();
    }

    @Test
    @DisplayName("Register and login user")
    void testUser() {
        var userController = new UserController();
        var userEmail = generateUserEmail();
        var expectedUser = buildUser(userEmail, defaultPassword);
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

        var userLoginResponse = userController.loginUser(new LoginRequest(userEmail, defaultPassword))
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





