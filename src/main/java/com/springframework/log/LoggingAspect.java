package com.springframework.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Created by Behrouz-ZD on 9/20/2017.
 */

@Aspect
@Component
public class LoggingAspect {

    private static Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.springframework.config.security.auth.CustomDaoAuthenticationProvider.authenticate( (*)))")
    public void executeBeforeLogin(JoinPoint jp) throws NoSuchMethodException {
        Authentication authentication = (Authentication) jp.getArgs()[0];
        logger.info("User {} has requested to login!", authentication.getName());
    }


    @AfterReturning("execution(* com.springframework.config.security.auth.CustomDaoAuthenticationProvider.authenticate( (*)))")
    public void executeAfterSuccessLogin(JoinPoint jp) throws NoSuchMethodException {
        Authentication authentication = (Authentication) jp.getArgs()[0];
        logger.info("User {} logged in successfully!", authentication.getName());
    }


    @AfterThrowing("execution(* com.springframework.config.security.auth.CustomDaoAuthenticationProvider.authenticate( (*)))")
    public void executeAfterFailedLogin(JoinPoint jp) throws NoSuchMethodException {
        Authentication authentication = (Authentication) jp.getArgs()[0];
        logger.info("User {} was not able to login!", authentication.getName());
    }

}
