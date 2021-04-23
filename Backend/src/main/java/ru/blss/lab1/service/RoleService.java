package ru.blss.lab1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.blss.lab1.domain.Courier;
import ru.blss.lab1.domain.User;
import ru.blss.lab1.exception.CourierAlreadyExistException;
import ru.blss.lab1.exception.UnauthorizedUserException;
import ru.blss.lab1.repository.CourierRepository;
import ru.blss.lab1.repository.RoleRepository;

@Service

public class RoleService {
    @Autowired
    private CourierRepository courierRepository;
    @Autowired
    private RoleRepository roleRepository;

    public void giveCourierRole(User user) throws UnauthorizedUserException, CourierAlreadyExistException {
        if (user == null) {
            throw new UnauthorizedUserException();
        }

        if (courierRepository.findById(user.getId()).isPresent())
            throw new CourierAlreadyExistException("You are already courier");
        if (!user.getUserRole().equals(roleRepository.findByName("ROLE_ADMIN").get())) {
            user.setUserRole(roleRepository.findByName("ROLE_COURIER").get());
        }
        Courier courier = new Courier();
        courier.setUser(user);
        courierRepository.save(courier);
    }
}
