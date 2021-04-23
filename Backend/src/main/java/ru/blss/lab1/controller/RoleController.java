package ru.blss.lab1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.blss.lab1.domain.User;
import ru.blss.lab1.exception.CourierAlreadyExistException;
import ru.blss.lab1.exception.UnauthorizedUserException;
import ru.blss.lab1.service.RoleService;
import ru.blss.lab1.service.UserService;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @PostMapping("/courier")
    public void upgradeToCourierRole(@RequestParam(name = "new_courier_login") String newCourier) throws UnauthorizedUserException, CourierAlreadyExistException {
        User user = userService.findByUsername(newCourier);
        roleService.giveCourierRole(user);
    }
}
