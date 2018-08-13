package com.the15373.dochub.dto;


import java.io.Serializable;

/**
 * excel记录数据传输对象
 * @author afang
 *
 * @date 2018年6月25日
 */

public class ExcelrecordDto implements Serializable {

	private static final long serialVersionUID = -2481696438475426128L;

	private String recordid;
	private ExcelDto excel;
	private UserDto user;
	private String record;
	private String time;
	public String getRecordid() {
		return recordid;
	}
	public void setRecordid(String recordid) {
		this.recordid = recordid;
	}
	public ExcelDto getExcel() {
		return excel;
	}
	public void setExcel(ExcelDto excel) {
		this.excel = excel;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
