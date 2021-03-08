package ru.blss.lab1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.blss.lab1.domain.User;

@RestController
@RequestMapping("/api/1")
public class UserController extends ApiController {
    @GetMapping("users/authorized")
    public User findAuthorized(User user) {
        return user;
    }
}
