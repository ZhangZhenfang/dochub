package com.the15373.dochub.service;



import java.util.List;
import java.util.Map;


import com.the15373.dochub.dto.UserDto;
import com.the15373.dochub.pojo.Relationship;
import com.the15373.dochub.pojo.User;

public interface RelationshipService {

	Relationship getRelationship(User user, Long friendid);

	Map<String, String> deleRelationship(Relationship relation);

	List<UserDto> getByUserid(Long userid);

	Map<String, String> addRelationship(String account, User user);
}
