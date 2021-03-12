package ru.blss.lab1.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.blss.lab1.controller.ApiController;
import ru.blss.lab1.domain.User;
import ru.blss.lab1.exception.ValidationException;
import ru.blss.lab1.service.JwtService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Optional;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    private static final String BEARER = "Bearer";

    private JwtService jwtService;

    @Autowired
    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            if (ApiController.class.isAssignableFrom(method.getDeclaringClass())) {
                String authorization = request.getHeader("Authorization");
                if (authorization != null && authorization.startsWith(BEARER)) {
                    String token = authorization.substring(BEARER.length()).trim();
                    Optional<User> user = jwtService.find(token);
                    if(user.isPresent()){
                        request.setAttribute("user", user.get());
                    }
                    else
                    {
                        throw new ValidationException("Invalid user token");
                    }
                }
            }
        }
        return true;
    }
}
