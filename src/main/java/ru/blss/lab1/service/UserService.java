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
        return login == null || password == null ? null : userRepository.findByLoginAndPassword(login, password);
    }

    public void register(UserRegisterCredentials userRegisterCredentials) {
        User user = new User();
        user.setLogin(userRegisterCredentials.getLogin());
        user.setName(userRegisterCredentials.getName());
        user.setSurname(userRegisterCredentials.getSurname());
        user.setEmail(userRegisterCredentials.getEmail());
        user.setOrganization(userRegisterCredentials.getOrganisation());
        user.setAdmin(false);
        userRepository.save(user);
        userRepository.updatePasswordSha(user.getId(), user.getLogin(), userRegisterCredentials.getPassword());
    }

    public List<User> findAll() {
        return userRepository.findAllByOrderByIdDesc();
    }


    public boolean isLoginVacant(String login) {
        return userRepository.countByLogin(login) == 0;
    }

}
