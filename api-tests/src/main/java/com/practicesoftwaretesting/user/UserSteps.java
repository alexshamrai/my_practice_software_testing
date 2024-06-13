package com.practicesoftwaretesting.user;

import com.github.javafaker.Faker;
import com.practicesoftwaretesting.ConfigReader;
import com.practicesoftwaretesting.user.model.LoginRequest;
import com.practicesoftwaretesting.user.model.RegisterUserRequest;
import com.practicesoftwaretesting.user.model.UsersSearch;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserSteps {

    ConfigReader config = new ConfigReader();

    public String registerUser(String userEmail, String password) {
        var userController = new UserController();
        var registerUserRequest = buildUser(userEmail, password);
        return userController.registerUser(registerUserRequest)
                .as().getId();
    }

    public String loginUser(String userEmail, String password) {
        var userController = new UserController();
        var userLoginResponse = userController.loginUser(new LoginRequest(userEmail, password))
                .as();
        assertNotNull(userLoginResponse.getAccessToken());
        return userLoginResponse.getAccessToken();
    }


    public void deleteUser(String userId) {
        var token = getAdminToken();
        new UserController().withToken(token).deleteUser(userId)
                .assertStatusCode(204);
    }

    public UsersSearch searchUsers(String queryPhrase) {
        var token = getAdminToken();
        return new UserController().withToken(token).searchUsers(queryPhrase)
                .as();
    }

    private String getAdminToken() {
        return loginUser(config.getProperty("admin.email"), config.getProperty("admin.password"));
    }

    public static RegisterUserRequest buildUser(String email, String password) {
        return RegisterUserRequest.builder()
                .firstName("John")
                .lastName("Lennon")
                .address("Street 1")
                .city("City")
                .state("State")
                .country("Ukraine")
                .postcode("1234AA")
                .phone("0987654321")
                .dob("1941-01-01")
                .email(email)
                .password(password)
                .build();
    }

    public static String generateUserEmail() {
        return Faker.instance()
                .friends()
                .character()
                .toLowerCase()
                .replaceAll(" ", "") + "@gmail.com";
    }
}