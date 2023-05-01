package com.funtravelapp.order.middleware;

import com.funtravelapp.order.ext.token.GetTokenAPI;
import com.funtravelapp.order.ext.token.dto.GetTokenResponse;
import com.funtravelapp.order.model.User;
import com.funtravelapp.order.validator.string.StringValidator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Aspect
@Component
public class AuthenticationAspect {
    @Autowired
    GetTokenAPI getTokenAPI;
    @Autowired
    StringValidator stringValidator;

    @Pointcut("execution(* com.funtravelapp.order.service.impl.OrderService.*(..))")
    private void orderServiceAuth(){}

    @Around("orderServiceAuth()")
    public Object checkToken(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();

        if (args.length < 3){
            return pjp.proceed();
        }

        String authHeader = (String) args[0];
        boolean isValid = stringValidator.setStr(authHeader).isValid();
        System.out.println(authHeader);
        if (!isValid) {
            throw new Exception("Unauthorized, invalid token");
        }

//        GetTokenResponse user = getTokenAPI.getToken(authHeader);
        GetTokenResponse u = GetTokenResponse.builder()
                .data(User.builder()
                        .id(1)
                        .email("user@gmail.com")
                        .expiredToken("2023-01-01")
                        .role("customer")
                        .username("admin")
                        .token("123")
                        .build())
                .build();
        Map<String, Boolean> allowedUser = (Map<String, Boolean>) args[1];

        if (!allowedUser.get(u.getData().getRole().toLowerCase())){
            throw new Exception("Unauthorized, user not allowed!");
        }

        args[2] = u.getData();

        return pjp.proceed(args);
    }
}
