package ru.blss.lab1.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.blss.lab1.domain.User;
import ru.blss.lab1.exception.CourierAlreadyExistException;
import ru.blss.lab1.exception.UnauthorizedUserException;
import ru.blss.lab1.repository.CourierRepository;
import ru.blss.lab1.repository.UserRepository;
import ru.blss.lab1.service.MessageService;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {
    private final UserRepository userRepository;
    private final CourierRepository courierRepository;
    private MessageService messageService;

    public RoleController(UserRepository userRepository, CourierRepository courierRepository, MessageService messageService) {
        this.userRepository = userRepository;
        this.courierRepository = courierRepository;
        this.messageService = messageService;
    }

    @PostMapping("/courier")
    public void upgradeToCourierRole(@RequestParam(name = "userId") long userId) throws UnauthorizedUserException, CourierAlreadyExistException {
        User user = userRepository.getOne(userId);

        if (user == null) {
            throw new UnauthorizedUserException();
        }

        if (courierRepository.findById(user.getId()).isPresent())
            throw new CourierAlreadyExistException("You are already courier");

        messageService.sendToGiveCourierRole(user);
    }
}
