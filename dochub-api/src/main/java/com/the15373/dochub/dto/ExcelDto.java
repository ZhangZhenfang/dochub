package com.the15373.dochub.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.the15373.dochub.pojo.Excel;
import com.the15373.dochub.util.DateUtil;
/**
 * Excel数据传输对象
 * @author afang
 *
 * @date 2018年6月25日
 */

public class ExcelDto implements Serializable {

	private static final long serialVersionUID = -1221513727390530194L;

	private String excelid;
	private UserDto user;
	private String starttime;
	private String deadline;
	private String description;
	private String filename;
	private String path;
	private String head;
	private ExcelrecordDto mysubmit; 
	public ExcelrecordDto getMysubmit() {
		return mysubmit;
	}


	public void setMysubmit(ExcelrecordDto mysubmit) {
		this.mysubmit = mysubmit;
	}
	private Set<ExcelrecordDto> excelrecords = new HashSet<ExcelrecordDto>(0);
	
	
	public ExcelDto toDto(Excel po) {
		ExcelDto dto = new ExcelDto();
		dto.setDeadline(DateUtil.sdf.format(po.getDeadline()));
		dto.setDescription(DateUtil.sdf.format(po.getDescription()));
		dto.setExcelid(po.getExcelid().toString());
		dto.setFilename(po.getFilename());
		dto.setStarttime(DateUtil.sdf.format(po.getStarttime()));
		
		return dto;
	}

	public String getExcelid() {
		return excelid;
	}
	public void setExcelid(String excelid) {
		this.excelid = excelid;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public Set<ExcelrecordDto> getExcelrecords() {
		return excelrecords;
	}
	public void setExcelrecords(Set<ExcelrecordDto> excelrecords) {
		this.excelrecords = excelrecords;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	@Override
	public String toString() {
		return "ExcelDto{" +
				"excelid='" + excelid + '\'' +
				", user=" + user +
				", starttime='" + starttime + '\'' +
				", deadline='" + deadline + '\'' +
				", description='" + description + '\'' +
				", filename='" + filename + '\'' +
				", path='" + path + '\'' +
				", head='" + head + '\'' +
				", mysubmit=" + mysubmit +
				", excelrecords=" + excelrecords +
				'}';
	}
}
