package com.the15373.dochub.dao;

import java.util.List;

import com.the15373.dochub.pojo.Notice;

public interface NoticeDao {

	void save(Notice notice);

	void update(Notice notice);

	List<Notice> getByUserId(Long userid);

	List<Notice> getNoticesFromFriends(Long userid);

	Notice getByUserIdAndId(Long userid, Long noticeid);

	void delete(Notice notice);

	Notice getByUserId(long noticeid);

}
