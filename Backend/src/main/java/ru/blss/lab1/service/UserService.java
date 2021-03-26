package ru.blss.lab1.service;

import org.springframework.stereotype.Service;
import ru.blss.lab1.domain.User;
import ru.blss.lab1.form.UserRegisterCredentials;
import ru.blss.lab1.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByLoginAndPassword(String login, String password) {
        return login == null || password == null ? null : userRepository.findAllByUsernameAndPassword(login, password);
    }

    public void register(UserRegisterCredentials userRegisterCredentials) {
        User user = new User();
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


    public boolean isLoginVacant(String login) {
        return userRepository.countByUsername(login) == 0;
    }

}
