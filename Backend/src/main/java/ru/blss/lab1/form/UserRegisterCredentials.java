package ru.blss.lab1.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegisterCredentials extends UserCredentials {
    @NotNull
    @NotBlank
    @Size(min = 2, max = 30)
    @Pattern(regexp = "[a-zA-Zа-яА-Я -]+", message = "Expected only letters in name")
    private String firstName;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 30)
    @Pattern(regexp = "[a-zA-Zа-яА-Я -]+", message = "Expected only letters in surname")
    private String lastName;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Incorrect email")
    private String email;

    public String getName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
