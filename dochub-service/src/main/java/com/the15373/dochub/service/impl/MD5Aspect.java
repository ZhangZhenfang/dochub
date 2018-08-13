package com.the15373.dochub.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.the15373.dochub.pojo.User;

/**
 * 
 * @author afang
 *
 * @date 2018年7月28日
 */

@Aspect
@Component
public class MD5Aspect {

	@Before("execution(* com.the15373.dochub.service.impl.UserServiceImpl.regist(com.the15373.dochub.pojo.User))")
	public void beforeMethod(JoinPoint point) {
		List<Object> args = Arrays.asList(point.getArgs());
		User user = (User)args.get(0);
		Md5Hash md5 = new Md5Hash(user.getPassword());
		user.setPassword(md5.toString());
	}
}
