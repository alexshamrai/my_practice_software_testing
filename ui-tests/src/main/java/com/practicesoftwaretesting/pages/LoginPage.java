package com.practicesoftwaretesting.pages;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.practicesoftwaretesting.utils.SelectorUtils.byDataTest;

public class LoginPage {

    public LoginPage isLoaded() {
        $("h3").shouldHave(text("Login"));
        return this;
    }

    public void clickRegisterYourAccountLink() {
        $(byDataTest("register-link")).click();
    }
}
