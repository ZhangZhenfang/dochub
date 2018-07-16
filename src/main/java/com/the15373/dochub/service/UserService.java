package com.the15373.dochub.service;

import java.util.Map;

import com.the15373.dochub.dto.AbstractResponse;
import com.the15373.dochub.pojo.User;

public interface UserService {
	User getByAccount(String account);

	Map<String, String> regist(User user);

	User auth(String account, String password);

	void login(String useragent, String ipAddr, User user);

	AbstractResponse getFriends(User user);

	Map<String, String> updateUserinfo(User user, User old);

	Map<String, String> updatePassword(String oldpassword, String newpassword, User user);

}
