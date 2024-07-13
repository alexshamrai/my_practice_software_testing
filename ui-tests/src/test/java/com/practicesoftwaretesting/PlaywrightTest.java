package com.practicesoftwaretesting;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.practicesoftwaretesting.user.model.RegisterUserRequest;
import org.junit.jupiter.api.*;

import static com.practicesoftwaretesting.user.UserSteps.generateUserEmail;

public class PlaywrightTest {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(300));
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    @DisplayName("Register new user and login")
    public void registerNewUserAndLogin() {
        page.navigate("https://practicesoftwaretesting.com");
        PlaywrightAssertions.assertThat(page.locator(".img-fluid")).isVisible();

        // Click header menu: Sign in
        page.locator("[data-test='nav-sign-in']").click();

        /// Login page is open
        PlaywrightAssertions.assertThat(page.locator("h3")).hasText("Login");
        page.locator("[data-test='register-link']").click();

        // Registration page is open
        PlaywrightAssertions.assertThat(page.locator("h3")).hasText("Customer registration");
        RegisterUserRequest user = getUser();
        page.locator("#first_name").fill(user.getFirstName());
        page.locator("#last_name").fill(user.getLastName());
        page.locator("#dob").fill(user.getDob());
        page.locator("#address").fill(user.getAddress());
        page.locator("#postcode").fill(user.getPostcode());
        page.locator("#city").fill(user.getCity());
        page.locator("#state").fill(user.getState());
        page.locator("#country").selectOption(user.getCountry());
        page.locator("#phone").fill(user.getPhone());
        page.locator("#email").fill(user.getEmail());
        page.locator("#password").fill(user.getPassword());
        page.locator("[data-test='register-submit']").click();

        // Login from login page
        PlaywrightAssertions.assertThat(page.locator("h3")).hasText("Login");
        page.locator("#email").fill(user.getEmail());
        page.locator("#password").fill(user.getPassword());
        page.locator("[data-test='login-submit']").click();

        // Account page is open and user is signed in
        PlaywrightAssertions.assertThat(page.locator("[data-test='page-title']")).hasText("My account");
        var fullUserName = user.getFirstName() + " " + user.getLastName();
        PlaywrightAssertions.assertThat(page.locator("[data-test='nav-menu']")).hasText(fullUserName);
        System.out.println("User is signed in: " + fullUserName);
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
                .dob("2000-01-01")
                .email(generateUserEmail())
                .password("12Example#")
                .build();
    }


}
