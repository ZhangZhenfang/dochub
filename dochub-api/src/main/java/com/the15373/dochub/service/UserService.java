package com.the15373.dochub.service;

import java.util.Map;

import com.the15373.dochub.dto.AbstractResponse;
import com.the15373.dochub.dto.UserDto;
import com.the15373.dochub.pojo.User;

public interface UserService {
	UserDto getByAccount(String account);

	Map<String, String> regist(UserDto user);

	UserDto auth(String account, String password);

	void login(String useragent, String ipAddr, UserDto user);

	AbstractResponse getFriends(UserDto user);

	Map<String, String> updateUserinfo(UserDto user, UserDto old);

	Map<String, String> updatePassword(String oldpassword, String newpassword, UserDto user);

}
