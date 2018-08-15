package com.the15373.dochub.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户数据传输对象
 * @author afang
 *
 * @date 2018年6月25日
 */

public class UserDto implements Serializable {

	private static final long serialVersionUID = -6027601019434139162L;

	private String userid;
	private String account;
	private String studentnumber;
	private String name;
	private String password;
	private String school;
	private String institute;
	private String major;
	private String classnumber;
	private String phonenumber;
	private String email;
	private String sex;
	private Set<NoticeDto> notices = new HashSet<NoticeDto>(0);
	private Set<ExcelDto> excels = new HashSet<ExcelDto>(0);
	private Set<FileDto> files = new HashSet<FileDto>(0);
	private Set<ExcelrecordDto> excelrecords = new HashSet<ExcelrecordDto>(0);
	private Set<RelationshipDto> relationships = new HashSet<RelationshipDto>(0);
	private Set<SharefileDto> sharefiles = new HashSet<SharefileDto>(0);
	private Set<UserlogDto> userlogs = new HashSet<UserlogDto>(0);
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getStudentnumber() {
		return studentnumber;
	}

	public void setStudentnumber(String studentnumber) {
		this.studentnumber = studentnumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getClassnumber() {
		return classnumber;
	}

	public void setClassnumber(String classnumber) {
		this.classnumber = classnumber;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<NoticeDto> getNotices() {
		return notices;
	}

	public void setNotices(Set<NoticeDto> notices) {
		this.notices = notices;
	}

	public Set<ExcelDto> getExcels() {
		return excels;
	}

	public void setExcels(Set<ExcelDto> excels) {
		this.excels = excels;
	}

	public Set<FileDto> getFiles() {
		return files;
	}

	public void setFiles(Set<FileDto> files) {
		this.files = files;
	}

	public Set<ExcelrecordDto> getExcelrecords() {
		return excelrecords;
	}

	public void setExcelrecords(Set<ExcelrecordDto> excelrecords) {
		this.excelrecords = excelrecords;
	}

	public Set<RelationshipDto> getRelationships() {
		return relationships;
	}

	public void setRelationships(Set<RelationshipDto> relationships) {
		this.relationships = relationships;
	}

	public Set<SharefileDto> getSharefiles() {
		return sharefiles;
	}

	public void setSharefiles(Set<SharefileDto> sharefiles) {
		this.sharefiles = sharefiles;
	}

	public Set<UserlogDto> getUserlogs() {
		return userlogs;
	}

	public void setUserlogs(Set<UserlogDto> userlogs) {
		this.userlogs = userlogs;
	}

	@Override
	public String toString() {
		return "UserDto{" +
				"userid='" + userid + '\'' +
				", account='" + account + '\'' +
				", studentnumber='" + studentnumber + '\'' +
				", name='" + name + '\'' +
				", password='" + password + '\'' +
				", school='" + school + '\'' +
				", institute='" + institute + '\'' +
				", major='" + major + '\'' +
				", classnumber='" + classnumber + '\'' +
				", phonenumber='" + phonenumber + '\'' +
				", email='" + email + '\'' +
				", sex='" + sex + '\'' +
				", notices=" + notices +
				", excels=" + excels +
				", files=" + files +
				", excelrecords=" + excelrecords +
				", relationships=" + relationships +
				", sharefiles=" + sharefiles +
				", userlogs=" + userlogs +
				'}';
	}
}
