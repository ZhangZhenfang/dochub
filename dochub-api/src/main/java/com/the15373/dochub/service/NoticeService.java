package com.the15373.dochub.service;

import java.util.Map;

import com.the15373.dochub.dto.UserDto;
import org.springframework.http.ResponseEntity;

import com.the15373.dochub.dto.AbstractResponse;
import com.the15373.dochub.pojo.Notice;
import com.the15373.dochub.pojo.User;

public interface NoticeService {

	Map<String, String> newNotice(Notice notice);

	AbstractResponse getMyNotice(UserDto user);

	AbstractResponse getNoticesFromFriends(UserDto user);

	Map<String, String> updateNotice(Notice notice, UserDto user);

	String downloadNotice(String baseDir, long parseLong, UserDto user) throws Exception;

	Map<String, String> deleteNotice(long noticeid, UserDto user);

}
