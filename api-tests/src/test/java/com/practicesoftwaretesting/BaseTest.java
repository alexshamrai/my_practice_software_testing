package com.practicesoftwaretesting;

import com.practicesoftwaretesting.user.UserSteps;
import com.practicesoftwaretesting.user.model.RegisterUserRequest;


public abstract class BaseTest {

    static ConfigReader configReader = new ConfigReader();
    static String defaultPassword = configReader.getProperty("default.password");
    static String adminEmail = configReader.getProperty("admin.email");
    static String adminPassword = configReader.getProperty("admin.password");

    UserSteps userSteps = new UserSteps();

    public void registerUser(String userEmail, String password) {
        userSteps.registerUser(userEmail, password);
    }

    public String loginUser(String userEmail, String password) {
        return userSteps.loginUser(userEmail, password);
    }

    public String registerAndLoginNewUser() {
        return userSteps.registerAndLoginNewUser();
    }

    public String loginAsAdmin() {
        return loginUser(adminEmail, adminPassword);
    }

    protected RegisterUserRequest buildUser(String email, String password) {
        return userSteps.buildUser(email, password);
    }

    protected String getUserEmail() {
        return userSteps.getUserEmail();
    }
}
