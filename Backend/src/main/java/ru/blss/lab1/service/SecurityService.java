package ru.blss.lab1.service;

import org.springframework.stereotype.Service;
import ru.blss.lab1.configs.JaasConfigs;
import ru.blss.lab1.security.CustomCallbackHandler;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

@Service
public class SecurityService {
    private Subject subject;

    public void login(String username, String password) throws LoginException {
        CustomCallbackHandler callbackHandler = new CustomCallbackHandler(username, password);

        LoginContext loginContext = new LoginContext(JaasConfigs.LOGIN_MODULE_CONFIG_NAME, callbackHandler);
        loginContext.login();

        subject = loginContext.getSubject();
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
