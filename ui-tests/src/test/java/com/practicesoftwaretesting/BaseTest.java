package com.practicesoftwaretesting;

import com.codeborne.selenide.Configuration;
import com.practicesoftwaretesting.pages.AccountPage;
import com.practicesoftwaretesting.pages.LoginPage;
import com.practicesoftwaretesting.user.UserSteps;
import org.junit.jupiter.api.AfterEach;

import static com.codeborne.selenide.FileDownloadMode.FOLDER;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.practicesoftwaretesting.user.UserSteps.DEFAULT_PASSWORD;

public abstract class BaseTest {

    static ConfigReader configReader = new ConfigReader();

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

    public void registerAndLoginAsNewUser() {
        var email = userSteps.getUserEmail();
        userSteps.registerUser(email, DEFAULT_PASSWORD);

        loginPage.open()
                .login(email, DEFAULT_PASSWORD);
        accountPage.isLoaded();
    }

    public void loginAsAdmin() {
        loginPage.open()
                .login(UserSteps.ADMIN_EMAIL, UserSteps.ADMIN_PASSWORD);
        accountPage.isLoaded();
    }

    public void login(String email, String password) {
        loginPage.open()
                .login(email, password);
        accountPage.isLoaded();
    }

}
