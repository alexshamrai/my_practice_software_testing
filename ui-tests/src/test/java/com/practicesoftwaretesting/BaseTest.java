package com.practicesoftwaretesting;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;

public abstract class BaseTest {

    static ConfigReader configReader = new ConfigReader();

    static {
        Configuration.baseUrl = configReader.getProperty("base.url");
    }

    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
    }

}
