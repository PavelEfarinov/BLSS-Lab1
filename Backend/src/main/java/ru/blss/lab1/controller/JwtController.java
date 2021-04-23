package ru.blss.lab1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.blss.lab1.exception.UnauthorizedUserException;
import ru.blss.lab1.exception.ValidationException;
import ru.blss.lab1.form.UserCredentials;
import ru.blss.lab1.form.UserRegisterCredentials;
import ru.blss.lab1.jwt.JwtProvider;
import ru.blss.lab1.service.SecurityService;
import ru.blss.lab1.service.UserService;
import ru.blss.lab1.util.BindingResultUtils;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class JwtController extends ApiController {

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityService securityService;

    @PostMapping("login")
    public String login(@RequestBody UserCredentials userCredentials) throws UnauthorizedUserException, LoginException {

        if (userCredentials.getUsername() == null || userCredentials.getPassword() == null)
            throw new UnauthorizedUserException("User Not Found");

        securityService.login(userCredentials.getUsername(), userCredentials.getPassword());

        return jwtProvider.generateToken(userCredentials.getUsername());
    }


    @PostMapping("register")
    public void register(@RequestBody @Valid UserRegisterCredentials userRegisterCredentials, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BindingResultUtils.getErrorMessage(bindingResult));
        }

        userService.register(userRegisterCredentials);
    }
}
