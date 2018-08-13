package com.the15373.dochub.dao;

import java.util.List;

import com.the15373.dochub.dto.UserDto;
import com.the15373.dochub.pojo.User;

public interface UserDao {

	User getByAccount(String account);

	void regist(User user);

	List<UserDto> getFriends(Long userid);

	void update(User user);

}
