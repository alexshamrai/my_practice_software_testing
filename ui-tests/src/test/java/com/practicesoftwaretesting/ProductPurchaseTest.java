package com.practicesoftwaretesting;

import com.practicesoftwaretesting.pages.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.practicesoftwaretesting.user.UserSteps.generateUserEmail;

public class ProductPurchaseTest extends BaseTest {

    HomePage homePage = new HomePage();
    ProductPage productPage = new ProductPage();
    Header header = new Header();
    CheckoutPage checkoutPage = new CheckoutPage();
    String userId;

    @BeforeEach
    void setup() {
        var email = generateUserEmail();
        userId = registerUser(email);
        login(email, defaultPassword);
    }

    @Test
    void addProductToCartAndPurchaseIt() {
        homePage.open()
                .isLoaded()
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

    @AfterEach
    void cleanup() {
        deleteUser(userId);
    }
}
