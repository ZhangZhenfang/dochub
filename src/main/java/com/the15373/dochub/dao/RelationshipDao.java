package com.the15373.dochub.dao;
import java.util.List;
import java.util.Map;

import com.the15373.dochub.dto.UserDto;
import com.the15373.dochub.pojo.Relationship;

public interface RelationshipDao {

	

	Map<String, String> addRelationship(Relationship relationship);

	Map<String, String> deleRelationship(Relationship relation);

	List<UserDto> getByUserid(Long userid);

	Relationship getByUseridAndFriendid(long friendid, long userid);

	

}
