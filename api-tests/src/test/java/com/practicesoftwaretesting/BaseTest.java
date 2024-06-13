package com.practicesoftwaretesting;

import com.practicesoftwaretesting.user.UserSteps;

public abstract class BaseTest {

    static ConfigReader configReader = new ConfigReader();
    static String defaultPassword = configReader.getProperty("default.password");
    static String adminEmail = configReader.getProperty("admin.email");
    static String adminPassword = configReader.getProperty("admin.password");

    UserSteps userSteps = new UserSteps();

    public String registerUser(String userEmail, String password) {
        return userSteps.registerUser(userEmail, password);
    }

    public String loginUser(String userEmail, String password) {
        return userSteps.loginUser(userEmail, password);
    }

    public String loginAsAdmin() {
        return loginUser(adminEmail, adminPassword);
    }

    public void deleteUser(String userId) {
        userSteps.deleteUser(userId);
    }
}
