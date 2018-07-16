package com.the15373.dochub.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author afang
 *
 * @date 2018年6月26日
 */

public class NoticeDto {
	
	private String noticeid;
	private UserDto user;
	private String starttime;
	private String deadline;
	private String path;
	private String notice;
	private String description;
	private FileDto mysubmt;
	private int type;
	private List<FileDto> files = new ArrayList<>();
	
	
	public FileDto getMysubmt() {
		return mysubmt;
	}
	public void setMysubmt(FileDto mysubmt) {
		this.mysubmt = mysubmt;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getNoticeid() {
		return noticeid;
	}
	public void setNoticeid(String noticeid) {
		this.noticeid = noticeid;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public List<FileDto> getFiles() {
		return files;
	}
	public void setFiles(List<FileDto> files) {
		this.files = files;
	}
	@Override
	public String toString() {
		return "NoticeDto [noticeid=" + noticeid + ", user=" + user + ", starttime=" + starttime + ", deadline="
				+ deadline + ", path=" + path + ", notice=" + notice + ", description=" + description + ", type=" + type
				+ ", files=" + files + "]";
	}
	
}
