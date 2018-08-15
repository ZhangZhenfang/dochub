package com.the15373.dochub.dao;

import com.the15373.dochub.dto.UserDto;
import com.the15373.dochub.pojo.User;

import java.util.List;

public interface UserDao {

	User getByAccount(String account);

	void regist(User user);

	List<UserDto> getFriends(Long userid);

	void update(User user);

}
