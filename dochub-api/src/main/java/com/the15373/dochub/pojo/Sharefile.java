package com.the15373.dochub.pojo;
// Generated 2018-6-24 22:12:53 by Hibernate Tools 5.2.8.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.the15373.dochub.dto.Dto;
import com.the15373.dochub.dto.SharefileDto;

/**
 * Sharefile generated by hbm2java
 */
@Entity
@Table(name = "sharefile", catalog = "test15373")
public class Sharefile implements java.io.Serializable, Dto<SharefileDto> {

	private static final long serialVersionUID = 1L;
	private Long id;
	private User user;
	private String filename;
	private String path;
	private String description;

	public Sharefile() {
	}

	public Sharefile(String filename, String description) {
		this.filename = filename;
		this.description = description;
	}

	public Sharefile(User user, String filename, String path, String description) {
		this.user = user;
		this.filename = filename;
		this.path = path;
		this.description = description;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "filename", nullable = false, length = 64)
	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "path", length = 65535)
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "description", nullable = false, length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public SharefileDto toDto() {
		// TODO Auto-generated method stub
		return null;
	}

}