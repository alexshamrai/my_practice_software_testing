package com.practicesoftwaretesting;

import com.codeborne.selenide.Configuration;
import com.practicesoftwaretesting.pages.AccountPage;
import com.practicesoftwaretesting.pages.LoginPage;
import com.practicesoftwaretesting.user.UserSteps;
import com.practicesoftwaretesting.user.model.UsersSearch;
import org.junit.jupiter.api.AfterEach;

import static com.codeborne.selenide.FileDownloadMode.FOLDER;
import static com.codeborne.selenide.Selenide.closeWebDriver;

public abstract class BaseTest {

    static ConfigReader configReader = new ConfigReader();
    static String defaultPassword = configReader.getProperty("default.password");
    static String adminEmail = configReader.getProperty("admin.email");
    static String adminPassword = configReader.getProperty("admin.password");

    UserSteps userSteps = new UserSteps();
    LoginPage loginPage = new LoginPage();
    AccountPage accountPage = new AccountPage();

    static {
        Configuration.baseUrl = configReader.getProperty("base.url");
        Configuration.timeout = Integer.parseInt(configReader.getProperty("timeout"));
        Configuration.browserSize = configReader.getProperty("browser.size");
        Configuration.clickViaJs = Boolean.parseBoolean(configReader.getProperty("click.via.js"));
        Configuration.fastSetValue = Boolean.parseBoolean(configReader.getProperty("fast.set.value"));
        Configuration.fileDownload = FOLDER;
        Configuration.headless = Boolean.parseBoolean(configReader.getProperty("headless"));
        Configuration.proxyEnabled = Boolean.parseBoolean(configReader.getProperty("proxy.enabled"));
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }

    public String registerUser(String email) {
        return userSteps.registerUser(email, defaultPassword);
    }

    public void loginAsAdmin() {
        loginPage.open()
                .login(adminEmail, adminPassword);
        accountPage.isLoaded();
    }

    public void login(String email, String password) {
        loginPage.open()
                .login(email, password);
        accountPage.isLoaded();
    }

    public void deleteUser(String userId) {
        userSteps.deleteUser(userId);
    }

    public UsersSearch searchUsers(String queryPhrase) {
        return userSteps.searchUsers(queryPhrase);
    }

}
