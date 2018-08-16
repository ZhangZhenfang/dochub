package com.the15373.dochub.user.aspect;

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
 * 用户注册切面
 *
 * @author afang
 *
 * @date 2018年7月28日
 */

@Aspect
@Component
public class RegistMD5HashAspect {
	static{
		System.out.println("@static{RegistMD5HashAspect}");
	}
	private static final Logger logger = LoggerFactory.getLogger(RegistMD5HashAspect.class);

	@Before("execution(* com.the15373.dochub.user.service.impl.UserServiceImpl.regist(com.the15373.dochub.dto.UserDto))")
	public void beforeMethod(JoinPoint point) {
		List<Object> args = Arrays.asList(point.getArgs());
		UserDto userDto = (UserDto) args.get(0);
		logger.info("before com.the15373.dochub.user.service.impl.UserServiceImpl.regist()" + userDto.toString());
		Md5Hash md5 = new Md5Hash(userDto.getPassword());
		userDto.setPassword(md5.toString());
	}

	@After("execution(* com.the15373.dochub.user.service.impl.UserServiceImpl.regist(com.the15373.dochub.dto.UserDto))")
	public void afterMethod(JoinPoint point) {
		List<Object> args = Arrays.asList(point.getArgs());
		UserDto userDto = (UserDto) args.get(0);
		logger.info("after com.the15373.dochub.user.service.impl.UserServiceImpl.regist()" + userDto.toString());
	}
}
