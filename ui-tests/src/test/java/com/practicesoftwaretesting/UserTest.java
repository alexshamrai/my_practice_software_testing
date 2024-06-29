package com.practicesoftwaretesting;

import com.practicesoftwaretesting.pages.*;
import com.practicesoftwaretesting.user.model.RegisterUserRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.practicesoftwaretesting.user.UserSteps.generateUserEmail;

public class UserTest extends BaseTest {

    HomePage homePage = new HomePage();
    Header header = new Header();
    LoginPage loginPage = new LoginPage();
    RegisterPage registerPage = new RegisterPage();
    AccountPage accountPage = new AccountPage();
    RegisterUserRequest user = getUser();

    @Test
    @Tag("Smoke")
    @DisplayName("Register new user and login")
    public void registerNewUserAndLogin() {
        homePage.open()
                .isLoaded();

        header.clickSignInMenuItem();
        loginPage.isLoaded()
                .clickRegisterYourAccountLink();

        registerPage.isLoaded()
                .assertThat()
                .hasCorrectInfo();

        registerPage.registerNewUser(user);

        loginPage.isLoaded()
                .login(user.getEmail(), user.getPassword());

        accountPage.isLoaded();
        var fullUserName = user.getFirstName() + " " + user.getLastName();
        header.assertThat()
                .isSignedIn(fullUserName);
    }

    @AfterEach
    void cleanup() {
        var users = searchUsers(user.getLastName());
        users.getData().forEach(userToDelete -> deleteUser(userToDelete.getId()));
    }

    private RegisterUserRequest getUser() {
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
                .email(generateUserEmail())
                .password("12Example#")
                .build();
    }
}
