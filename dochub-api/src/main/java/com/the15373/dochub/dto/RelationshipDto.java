package com.the15373.dochub.dto;

import java.io.Serializable;

/**
 * 用户关系数据传输对象
 * @author afang
 *
 * @date 2018年6月25日
 */
public class RelationshipDto implements Serializable {

	private static final long serialVersionUID = -6535135711190459993L;

	private String relationshipid;
	private UserDto user;
	private String time;
	private String friendid;
	public String getRelationshipid() {
		return relationshipid;
	}
	public void setRelationshipid(String relationshipid) {
		this.relationshipid = relationshipid;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getFriendid() {
		return friendid;
	}
	public void setFriendid(String friendid) {
		this.friendid = friendid;
	}
}
