package ru.blss.lab1.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.blss.lab1.exception.ValidationException;
import ru.blss.lab1.form.UserCredentials;
import ru.blss.lab1.form.UserRegisterCredentials;
import ru.blss.lab1.form.validator.UserCredentialsEnterValidator;
import ru.blss.lab1.service.JwtService;
import ru.blss.lab1.service.UserService;
import ru.blss.lab1.util.BindingResultUtils;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class JwtController extends ApiController {
    private final JwtService jwtService;
    private final UserCredentialsEnterValidator userCredentialsEnterValidator;
    private final UserService userService;

    public JwtController(JwtService jwtService, UserCredentialsEnterValidator userCredentialsEnterValidator, UserService userService) {
        this.jwtService = jwtService;
        this.userCredentialsEnterValidator = userCredentialsEnterValidator;
        this.userService = userService;
    }

    @InitBinder("userCredentials")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userCredentialsEnterValidator);
    }

    @PostMapping("jwt")
    public String createJwt(@RequestBody @Valid UserCredentials userCredentials, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BindingResultUtils.getErrorMessage(bindingResult));
        }

        return jwtService.create(userCredentials.getUsername(), userCredentials.getPassword());
    }


    @PostMapping("register")
    public void register(@RequestBody @Valid UserRegisterCredentials userRegisterCredentials, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BindingResultUtils.getErrorMessage(bindingResult));
        }

        userService.register(userRegisterCredentials);
    }
}
