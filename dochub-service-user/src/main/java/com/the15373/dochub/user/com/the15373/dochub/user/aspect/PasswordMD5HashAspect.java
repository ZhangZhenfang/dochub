package com.the15373.dochub.user.com.the15373.dochub.user.aspect;

import com.the15373.dochub.dto.UserDto;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author afang
 *
 * @date 2018年8月15日
 *
 */

@Aspect
@Component
public class PasswordMD5HashAspect {
    private static final Logger logger = LoggerFactory.getLogger(PasswordMD5HashAspect.class);

    @Before("execution(* com.the15373.dochub.user.service.impl.UserServiceImpl.auth(java.lang.String, java.lang.StringBuffer))")
    public void beforeMethod(JoinPoint point) {
        List<Object> args = Arrays.asList(point.getArgs());
        String arg0 = (String) args.get(0);
        StringBuffer arg1 = (StringBuffer) args.get(1);
        logger.info("before com.the15373.dochub.user.service.impl.UserServiceImpl.auth()" + arg0 + ", " + arg1);
        Md5Hash md5 = new Md5Hash(arg1.toString());
        arg1.replace(0, arg1.length(), md5.toString());
    }

    @After("execution(* com.the15373.dochub.user.service.impl.UserServiceImpl.auth(java.lang.String, java.lang.StringBuffer))")
    public void afterMethod(JoinPoint point) {
        List<Object> args = Arrays.asList(point.getArgs());
        String arg0 = (String) args.get(0);
        StringBuffer arg1 = (StringBuffer) args.get(1);
        logger.info("after com.the15373.dochub.user.service.impl.UserServiceImpl.auth()" + arg0 + ", " + arg1);
    }

    @Before("execution(* com.the15373.dochub.user.service.impl.UserServiceImpl.updatePassword(java.lang.StringBuffer, java.lang.StringBuffer, com.the15373.dochub.dto.UserDto))")
    public void beforeUpdatePassword(JoinPoint point){
        List<Object> args = Arrays.asList(point.getArgs());
        StringBuffer arg0 = (StringBuffer) args.get(0);
        StringBuffer arg1 = (StringBuffer) args.get(1);
        UserDto arg2 = (UserDto) args.get(2);
        logger.info("before com.the15373.dochub.user.service.impl.UserServiceImpl.updatePassword()" + arg0 + ", " + arg1);
        Md5Hash md5 = new Md5Hash(arg1.toString());
        arg1.replace(0, arg1.length(), md5.toString());
        md5 = new Md5Hash(arg0.toString());
        arg0.replace(0, arg0.length(), md5.toString());
    }
    @After("execution(* com.the15373.dochub.user.service.impl.UserServiceImpl.updatePassword(java.lang.StringBuffer, java.lang.StringBuffer, com.the15373.dochub.dto.UserDto))")
    public void afterUpdatePassword(JoinPoint point){
        List<Object> args = Arrays.asList(point.getArgs());
        StringBuffer arg0 = (StringBuffer) args.get(0);
        StringBuffer arg1 = (StringBuffer) args.get(1);
        UserDto arg2 = (UserDto) args.get(2);
        logger.info("after com.the15373.dochub.user.service.impl.UserServiceImpl.updatePassword()" + arg0 + ", " + arg1);
    }
}
