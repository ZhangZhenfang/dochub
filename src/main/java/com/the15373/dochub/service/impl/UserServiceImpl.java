package com.the15373.dochub.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.the15373.dochub.dao.UserDao;
import com.the15373.dochub.dao.UserlogDao;
import com.the15373.dochub.dto.AbstractResponse;
import com.the15373.dochub.dto.DefaultResponse;
import com.the15373.dochub.dto.UserDto;
import com.the15373.dochub.pojo.User;
import com.the15373.dochub.pojo.Userlog;
import com.the15373.dochub.service.UserService;

/**
 * 
 * @author afang
 *
 * @date 2018年6月25日
 */

@Transactional
@Service
public class UserServiceImpl implements UserService{

	@Resource
	private UserDao userDao;
	@Resource
	private UserlogDao userlogDao;
	@Override
	public User getByAccount(String account) {
		System.out.println(account);
		return userDao.getByAccount(account);
	}

	@Override
	public Map<String, String> regist(User user) {
		Map<String, String> res = new HashMap<>();
		User u = userDao.getByAccount(user.getStudentnumber());
		if(u == null) {
			userDao.regist(user);
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
	public User auth(String account, String password) {
		User u = userDao.getByAccount(account);
		if(u != null) {
			if(u.getPassword().equals(password)) {
				return u;
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
	public void login(String useragent, String ipAddr, User user) {
		Userlog userlog = new Userlog();
		userlog.setIp(ipAddr);
		userlog.setTime(new Date());
		userlog.setUser(user);
		userlog.setUseragent(useragent);
		userlogDao.save(userlog);
		
	}

	@Override
	public AbstractResponse getFriends(User user) {
		DefaultResponse<List<UserDto>> res = new DefaultResponse<>();
		List<UserDto> data = userDao.getFriends(user.getUserid());
		res.setData(data);
		res.setStatus("1");
		return res;
	}
	
	@Override
	public Map<String, String> updateUserinfo(User user, User old) {
		old.setName(user.getName());
		old.setEmail(user.getEmail());
		old.setSchool(user.getSchool());
		old.setInstitute(user.getInstitute());
		old.setMajor(user.getMajor());
		old.setPhonenumber(user.getPhonenumber());
		userDao.update(old);
		Map<String, String> res = new HashMap<>();
		res.put("status", "1");
		return res;
	}

	@Override
	public Map<String, String> updatePassword(String oldpassword, String newpassword, User user) {
		if(oldpassword.equals(user.getPassword())) {
			user.setPassword(newpassword);
			userDao.update(user);
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
