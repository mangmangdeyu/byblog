package com.zby.aspect;

import com.zby.pojo.UserInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.zby.controller.*.*(..))")
    public void doBefore(JoinPoint jp){

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =attributes.getRequest();
        String url = request.getRequestURL().toString();
        String uri = request.getRequestURI().toString();
        String ip = request.getRemoteAddr();
        String classMethod = jp.getSignature().getDeclaringTypeName()+"."+jp.getSignature().getName();
        Object[] args = jp.getArgs();
        UserInfo info = new UserInfo(url,ip,classMethod,args,uri);
        logger.info("Request:{}",info);
    }

    @After("execution(* com.zby.controller.*.*(..))")
    public void doAfter(JoinPoint jp){
        logger.info("after.....");
    }

    @AfterReturning(returning = "result",pointcut = "execution(* com.zby.controller.*.*(..))")
    public void AfterReturning(Object result){
        logger.info("Result : {}"+ result);

    }
}
