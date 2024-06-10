package com.practicesoftwaretesting;

import com.practicesoftwaretesting.pages.*;
import com.practicesoftwaretesting.user.model.RegisterUserRequest;
import org.junit.jupiter.api.Test;

public class UserTest extends BaseTest {

    HomePage homePage = new HomePage();
    Header header = new Header();
    LoginPage loginPage = new LoginPage();
    RegisterPage registerPage = new RegisterPage();
    AccountPage accountPage = new AccountPage();

    @Test
    public void registerNewUserAndLogin() {
        homePage.open()
                .isLoaded();

        header.clickSignInMenuItem();
        loginPage.isLoaded()
                .clickRegisterYourAccountLink();

        registerPage.isLoaded()
                .assertThat()
                .hasCorrectInfo();

        var user = getUser();
        registerPage.registerNewUser(user);

        loginPage.isLoaded()
                .login(user.getEmail(), user.getPassword());

        accountPage.isLoaded();
        var fullUserName = user.getFirstName() + " " + user.getLastName();
        header.assertThat()
                .isSignedIn(fullUserName);
    }

    private static RegisterUserRequest getUser() {
        return RegisterUserRequest.builder()
                .firstName("George")
                .lastName("Harrison")
                .address("123 Main St")
                .city("Liverpool")
                .state("Merseyside")
                .country("Ukraine")
                .postcode("12345")
                .phone("1234567890")
                .dob("01/01/2000")
                .email("georgeharrison@gmail.com")
                .password("12Example#")
                .build();
    }
}
