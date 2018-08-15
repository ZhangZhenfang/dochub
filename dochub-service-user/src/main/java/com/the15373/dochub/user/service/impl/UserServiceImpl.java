package com.the15373.dochub.user.service.impl;

import com.the15373.dochub.dao.UserDao;
import com.the15373.dochub.dao.UserlogDao;
import com.the15373.dochub.dto.AbstractResponse;
import com.the15373.dochub.dto.DefaultResponse;
import com.the15373.dochub.dto.UserDto;
import com.the15373.dochub.pojo.User;
import com.the15373.dochub.pojo.Userlog;
import com.the15373.dochub.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author afang
 *
 * @date 2018年6月25日
 */

@Transactional
@Service
public class UserServiceImpl implements UserService{
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Resource
	private UserDao userDao;
	@Resource
	private UserlogDao userlogDao;
	@Override
	public UserDto getByAccount(String account) {
		System.out.println(account);
		return userDao.getByAccount(account).toDto();
	}

	@Override
	public Map<String, String> regist(UserDto user) {
		Map<String, String> res = new HashMap<>();
		System.out.println("regist() parms :" + user.toString());
		User u = userDao.getByAccount(user.getStudentnumber());
		if(u == null) {
			u = new User();
			u.setPassword(user.getPassword());
			u.setEmail(user.getEmail());
			u.setPhonenumber(user.getPhonenumber());
			u.setName(user.getName());
			u.setAccount(user.getAccount());
			u.setClassnumber(user.getClassnumber());
			u.setMajor(user.getMajor());
			u.setInstitute(user.getInstitute());
			u.setSchool(user.getSchool());
			u.setSex(user.getSex());
			u.setStudentnumber(user.getStudentnumber());
			userDao.regist(u);
			res.put("status", 1 + "");
			return res;
		}
		else {
			res.put("status", "001003");
			res.put("errors", "用户已存在");
			return res;
		}
	}

	@Override
	public UserDto auth(String account, StringBuffer password) {
		logger.info("auth() parms : " + account + ", " + password);
		User u = userDao.getByAccount(account);
		if(u != null) {
			if(u.getPassword().equals(password.toString())) {
				UserDto res = u.toDto();
				System.out.println(res);
				return res;
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	}

	@Override
	public void login(String useragent, String ipAddr, UserDto user) {
		Userlog userlog = new Userlog();
		userlog.setIp(ipAddr);
		userlog.setTime(new Date());
		User u = userDao.getByAccount(user.getAccount());
		userlog.setUser(u);
		userlog.setUseragent(useragent);
		userlogDao.save(userlog);
		
	}

	@Override
	public AbstractResponse getFriends(UserDto user) {
		DefaultResponse<List<UserDto>> res = new DefaultResponse<>();
		List<UserDto> data = userDao.getFriends(Long.parseLong(user.getUserid()));
		res.setData(data);
		res.setStatus("1");
		return res;
	}
	
	@Override
	public Map<String, String> updateUserinfo(UserDto user, UserDto old) {
		User oldUser = userDao.getByAccount(user.getAccount());
		oldUser.setName(user.getName());
		oldUser.setEmail(user.getEmail());
		oldUser.setSchool(user.getSchool());
		oldUser.setInstitute(user.getInstitute());
		oldUser.setMajor(user.getMajor());
		oldUser.setPhonenumber(user.getPhonenumber());
		userDao.update(oldUser);
		Map<String, String> res = new HashMap<>();
		res.put("status", "1");
		return res;
	}

	@Override
	public Map<String, String> updatePassword(StringBuffer oldpassword, StringBuffer newpassword, UserDto user) {
		User u = userDao.getByAccount(user.getAccount());
		if(oldpassword.equals(user.getPassword())) {
			u.setPassword(newpassword.toString());
			userDao.update(u);
			Map<String, String> res = new HashMap<>();
			res.put("status", "1");
			return res;
		}
		else {
			Map<String, String> res = new HashMap<>();
			res.put("status", "001003");
			res.put("errors", "原密码密码错误");
			return res;
		}
	}
}
