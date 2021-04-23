package ru.blss.lab1.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.blss.lab1.configs.JaasConfigs;
import ru.blss.lab1.exception.UnauthorizedUserException;
import ru.blss.lab1.exception.ValidationException;
import ru.blss.lab1.form.UserCredentials;
import ru.blss.lab1.form.UserRegisterCredentials;
import ru.blss.lab1.form.validator.UserCredentialsEnterValidator;
import ru.blss.lab1.security.CustomCallbackHandler;
import ru.blss.lab1.service.JwtService;
import ru.blss.lab1.service.SecurityService;
import ru.blss.lab1.service.UserService;
import ru.blss.lab1.util.BindingResultUtils;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class JwtController extends ApiController {
    private final JwtService jwtService;
    private final UserService userService;
    private final SecurityService securityService;

    public JwtController(JwtService jwtService, UserService userService, SecurityService securityService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.securityService = securityService;
    }

    @PostMapping("login")
    public String login(@RequestBody UserCredentials userCredentials) throws UnauthorizedUserException, LoginException {

        if (userCredentials.getUsername() == null || userCredentials.getPassword() == null)
            throw new UnauthorizedUserException("User Not Found");

        securityService.login(userCredentials.getUsername(), userCredentials.getPassword());

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
