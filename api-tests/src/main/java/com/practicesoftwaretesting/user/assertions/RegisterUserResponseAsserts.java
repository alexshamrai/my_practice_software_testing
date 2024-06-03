package com.practicesoftwaretesting.user.assertions;

import com.practicesoftwaretesting.user.model.RegisterUserResponse;
import lombok.AllArgsConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@AllArgsConstructor
public class RegisterUserResponseAsserts {

    private RegisterUserResponse registerUserResponse;

    public RegisterUserResponseAsserts idIsNotNull() {
        assertThat(registerUserResponse.getId())
                .withFailMessage("id is null")
                .isNotNull();
        return this;
    }

    public RegisterUserResponseAsserts firstNameIs(String expectedTitle) {
        assertThat(registerUserResponse.getFirstName())
                .withFailMessage(String.format(
                        "firstName should be %s but was %s",
                        expectedTitle,
                        registerUserResponse.getFirstName()
                ))
                .isEqualTo(expectedTitle);
        return this;
    }

    public RegisterUserResponseAsserts lastNameIs(String expectedTitle) {
        assertThat(registerUserResponse.getLastName())
                .withFailMessage(String.format(
                        "lastName should be %s but was %s",
                        expectedTitle,
                        registerUserResponse.getLastName()
                ))
                .isEqualTo(expectedTitle);
        return this;
    }

    public RegisterUserResponseAsserts countryIs(String expectedTitle) {
        assertThat(registerUserResponse.getCountry())
                .withFailMessage(String.format(
                        "country should be %s but was %s",
                        expectedTitle,
                        registerUserResponse.getCountry()
                ))
                .isEqualTo(expectedTitle);
        return this;
    }

    public RegisterUserResponseAsserts postcodeIs(String expectedTitle) {
        assertThat(registerUserResponse.getPostcode())
                .withFailMessage(String.format(
                        "postcode should be %s but was %s",
                        expectedTitle,
                        registerUserResponse.getPostcode()
                ))
                .isEqualTo(expectedTitle);
        return this;
    }

    public RegisterUserResponseAsserts emailIs(String expectedTitle) {
        assertThat(registerUserResponse.getEmail())
                .withFailMessage(String.format(
                        "email should be %s but was %s",
                        expectedTitle,
                        registerUserResponse.getEmail()
                ))
                .isEqualTo(expectedTitle);
        return this;
    }

    public RegisterUserResponseAsserts createdAtIsNotNull() {
        assertThat(registerUserResponse.getCreatedAt())
                .withFailMessage("createdAt is null")
                .isNotNull();
        return this;
    }
}
