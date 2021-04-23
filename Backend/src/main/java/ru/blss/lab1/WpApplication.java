package ru.blss.lab1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.blss.lab1.configs.JaasConfigs;
import ru.blss.lab1.configs.SpringContextHolder;

@SpringBootApplication
public class WpApplication implements WebMvcConfigurer {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(WpApplication.class, args);
        SpringContextHolder.setApplicationContext(applicationContext);
        System.setProperty(JaasConfigs.DEFAULT_LOGIN_MODULE, JaasConfigs.LOGIN_MODULE_PATH);
    }
}
