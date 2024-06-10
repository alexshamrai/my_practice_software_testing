package com.practicesoftwaretesting.pages;

import org.openqa.selenium.By;
import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.practicesoftwaretesting.utils.SelectorUtils.byDataTest;

public class LoginPage {

    private static final By EMAIL_INPUT = byDataTest("email");
    private static final By PASSWORD_INPUT = byDataTest("password");
    private static final By LOGIN_BUTTON = byDataTest("login-submit");

    public LoginPage open() {
        Selenide.open("/auth/login");
        return this;
    }

    public LoginPage isLoaded() {
        $("h3").shouldHave(text("Login"));
        return this;
    }

    public void clickRegisterYourAccountLink() {
        $(byDataTest("register-link")).click();
    }

    public void login(String email, String password) {
        $(EMAIL_INPUT).setValue(email);
        $(PASSWORD_INPUT).setValue(password);
        $(LOGIN_BUTTON).click();
    }
}
