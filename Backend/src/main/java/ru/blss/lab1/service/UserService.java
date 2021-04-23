package ru.blss.lab1.service;

import org.springframework.stereotype.Service;
import ru.blss.lab1.domain.User;
import ru.blss.lab1.form.UserRegisterCredentials;
import ru.blss.lab1.repository.RoleRepository;
import ru.blss.lab1.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User findByLoginAndPassword(String login, String password) {
        return login == null || password == null ? null : userRepository.findAllByUsernameAndPassword(login, password);
    }

    public void register(UserRegisterCredentials userRegisterCredentials) {
        User user = new User();

        user.setUserRole(roleRepository.findByName("ROLE_USER").get());
        user.setUsername(userRegisterCredentials.getUsername());
        user.setFirstName(userRegisterCredentials.getName());
        user.setLastName(userRegisterCredentials.getLastName());
        user.setEmail(userRegisterCredentials.getEmail());
        user.setPassword(userRegisterCredentials.getPassword());
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAllByOrderByIdDesc();
    }

    public User findByUsername(String login) {
        return userRepository.findByUsername(login);
    }

    public boolean isLoginVacant(String login) {
        return userRepository.countByUsername(login) == 0;
    }

}
