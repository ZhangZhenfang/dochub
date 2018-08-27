package com.the15373.dochub.dto;


import java.io.Serializable;

/**
 * 文件数据传输对象
 * @author afang
 *
 * @date 2018年6月25日
 */
public class FileDto implements Serializable {

	private static final long serialVersionUID = -2622088507571195692L;

	private String fileid;
	private NoticeDto notice;
	private UserDto user;
	private String filename;
	private String path;
	private String time;
	private String md5;
	
	public String getFileid() {
		return fileid;
	}
	public void setFileid(String fileid) {
		this.fileid = fileid;
	}
	public NoticeDto getNotice() {
		return notice;
	}
	public void setNotice(NoticeDto notice) {
		this.notice = notice;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}
}
