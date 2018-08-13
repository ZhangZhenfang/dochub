package com.the15373.dochub.pojo;
// Generated 2018-6-24 22:12:53 by Hibernate Tools 5.2.8.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.the15373.dochub.dto.Dto;
import com.the15373.dochub.dto.RelationshipDto;
import com.the15373.dochub.util.DateUtil;

/**
 * Relationship generated by hbm2java
 */
@Entity
@Table(name = "relationship", catalog = "test15373")
public class Relationship implements java.io.Serializable, Dto<RelationshipDto> {

	private static final long serialVersionUID = 1L;
	private Long relationshipid;
	private User user;
	private Date time;
	private long friendid;

	public Relationship() {
	}

	public Relationship(Date time, long friendid) {
		this.time = time;
		this.friendid = friendid;
	}

	public Relationship(User user, Date time, long friendid) {
		this.user = user;
		this.time = time;
		this.friendid = friendid;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "relationshipid", unique = true, nullable = false)
	public Long getRelationshipid() {
		return this.relationshipid;
	}

	public void setRelationshipid(Long relationshipid) {
		this.relationshipid = relationshipid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "time", nullable = false, length = 19)
	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Column(name = "friendid", nullable = false)
	public long getFriendid() {
		return this.friendid;
	}

	public void setFriendid(long friendid) {
		this.friendid = friendid;
	}

	@Override
	public RelationshipDto toDto() {
		RelationshipDto dto = new RelationshipDto();
		dto.setRelationshipid(this.getRelationshipid().toString());
		dto.setUser(this.getUser().toDto());
		dto.setTime(DateUtil.sdf.format(this.getTime()));
		dto.setFriendid(this.getFriendid() + "");
		return dto;
	}

}