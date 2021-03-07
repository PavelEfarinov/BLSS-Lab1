package ru.blss.lab1.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import ru.blss.lab1.domain.User;

import javax.servlet.http.HttpServletRequest;

public abstract class ApiController {
    @ModelAttribute
    public User getUser(HttpServletRequest httpServletRequest) {
        return (User) httpServletRequest.getAttribute("user");
    }
}
