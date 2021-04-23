package ru.blss.lab1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.blss.lab1.configs.JaasConfigs;
import ru.blss.lab1.configs.SpringContextHolder;
import ru.blss.lab1.interceptor.JwtInterceptor;
import ru.blss.lab1.security.CustomCallbackHandler;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

@SpringBootApplication
public class WpApplication implements WebMvcConfigurer {
    private JwtInterceptor jwtInterceptor;
    @Autowired
    public void setJwtInterceptor(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor);
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(WpApplication.class, args);
        SpringContextHolder.setApplicationContext(applicationContext);
        System.setProperty(JaasConfigs.DEFAULT_LOGIN_MODULE, JaasConfigs.LOGIN_MODULE_PATH);
    }

}
