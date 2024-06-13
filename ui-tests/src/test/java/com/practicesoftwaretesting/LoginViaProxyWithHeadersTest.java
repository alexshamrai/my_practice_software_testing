package com.practicesoftwaretesting;

import com.codeborne.selenide.AuthenticationType;
import com.codeborne.selenide.BearerTokenCredentials;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.proxy.SelenideProxyServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.practicesoftwaretesting.user.UserSteps.generateUserEmail;

public class LoginViaProxyWithHeadersTest extends BaseTest {

    String userId;
    String token;

    @BeforeEach
    void setup() {
        var email = generateUserEmail();
        userId = registerUser(email);
        token = getLoginToken(email, defaultPassword);
    }

    @Test
    void loginViaProxyWithHeaders() {
        open("/");
        WebDriverRunner.getSelenideProxy()
                .getProxy()
                .addHeader("Authorization", "Bearer " + token);
        open("/contact");

        Selenide.sleep(10000);
    }

    @Test
    void loginViaProxyWithHeaders2() {
        open("/contact", AuthenticationType.BEARER, new BearerTokenCredentials("", token));

        Selenide.sleep(20000);
    }

    @AfterEach
    void cleanup() {
        deleteUser(userId);
    }
}
