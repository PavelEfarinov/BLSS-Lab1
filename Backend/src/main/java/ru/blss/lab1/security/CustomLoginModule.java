package ru.blss.lab1.security;

import com.sun.security.auth.UserPrincipal;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.blss.lab1.configs.SpringContextHolder;
import ru.blss.lab1.domain.User;
import ru.blss.lab1.repository.UserRepository;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

public class CustomLoginModule implements LoginModule {
    private UserRepository userRepository;
    private Subject subject;
    private CallbackHandler callbackHandler;
    private Map<String, ?> sharedState;
    private Map<String, ?> options;
    private boolean loginSucceeded = false;
    private Principal userPrincipal;


    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.callbackHandler = callbackHandler;
        this.subject = subject;
        this.sharedState = sharedState;
        this.options = options;
        this.userRepository = SpringContextHolder.getApplicationContext().getBean("userRepository", UserRepository.class);
    }

    @Override
    public boolean login() throws LoginException {
        NameCallback nameCallback = new NameCallback("username");
        PasswordCallback passwordCallback = new PasswordCallback("password", false);

        try {
            callbackHandler.handle(new Callback[]{nameCallback, passwordCallback});

            String username = nameCallback.getName();
            String password = new String(passwordCallback.getPassword());

            User user = userRepository.findAllByUsernameAndPassword(username, password);

            if (user != null)
            {
                loginSucceeded = true;
                userPrincipal = new UserPrincipal(username);
                subject.getPrincipals().add(userPrincipal);
            }
        } catch (IOException | UnsupportedCallbackException e) {
            e.printStackTrace();
        }

        return loginSucceeded;
    }

    @Override
    public boolean commit() throws LoginException {
        return loginSucceeded;
    }

    @Override
    public boolean abort() throws LoginException {
        return false;
    }

    @Override
    public boolean logout() throws LoginException {
        return false;
    }

}
