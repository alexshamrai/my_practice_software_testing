package com.practicesoftwaretesting;

import com.practicesoftwaretesting.pages.*;
import com.practicesoftwaretesting.user.UserSteps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.practicesoftwaretesting.user.UserSteps.DEFAULT_PASSWORD;

public class ProductPurchaseTest {

    UserSteps userSteps = new UserSteps();
    LoginPage loginPage = new LoginPage();
    HomePage homePage = new HomePage();
    ProductPage productPage = new ProductPage();
    AccountPage accountPage = new AccountPage();
    Header header = new Header();
    CheckoutPage checkoutPage = new CheckoutPage();

    @BeforeEach
    void setup() {
        var email = userSteps.getUserEmail();
        userSteps.registerUser(email, DEFAULT_PASSWORD);

        open("https://practicesoftwaretesting.com/#/auth/login");
        loginPage.login(email, DEFAULT_PASSWORD);
        accountPage.isLoaded();

    }

    @Test
    void addProductToCartAndPurchaseIt() {
        open("https://practicesoftwaretesting.com/#/");
        homePage.isLoaded()
                .clickOnTheFirstProduct();

        productPage.isLoaded()
                .addToCart();

        header.clickCartMenuItem();
        checkoutPage.isLoaded()
                .proceedToCheckout()
                .proceedToCheckoutSignedIn()
                .proceedToCheckoutBillingAddress()
                .choseCashPaymentMethodAndConfirm()
                .assertThat()
                .successMessageIsDisplayed();
    }
}
