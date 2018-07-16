package com.the15373.dochub.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.the15373.dochub.dto.AbstractResponse;
import com.the15373.dochub.pojo.Notice;
import com.the15373.dochub.pojo.User;

public interface NoticeService {

	Map<String, String> newNotice(Notice notice);

	AbstractResponse getMyNotice(User user);

	AbstractResponse getNoticesFromFriends(User user);

	Map<String, String> updateNotice(Notice notice, User user);

	ResponseEntity<byte[]> downloadNotice(String baseDir, long parseLong, User user) throws Exception;

	Map<String, String> deleteNotice(long noticeid, User user);

}
