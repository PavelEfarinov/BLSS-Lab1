package ru.blss.lab1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.blss.lab1.domain.User;
import ru.blss.lab1.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;

public abstract class ApiController {

    @Autowired
    UserRepository userRepository;

    @ModelAttribute
    public User getUser(HttpServletRequest httpServletRequest) {
        User requestUser = (User) httpServletRequest.getAttribute("user");
        if(requestUser != null) {
            return userRepository.getOne(requestUser.getId());
        }
        // If the user is null => login request, or no token provided
        return requestUser;
    }
}
