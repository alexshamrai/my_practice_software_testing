package com.practicesoftwaretesting;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;

import static com.codeborne.selenide.FileDownloadMode.FOLDER;
import static com.codeborne.selenide.Selenide.closeWebDriver;

public abstract class BaseTest {

    static ConfigReader configReader = new ConfigReader();

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

}
