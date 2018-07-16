package com.the15373.dochub.dao.impl;
import org.springframework.stereotype.Repository;

import com.the15373.dochub.dao.RelationshipDao;


import com.the15373.dochub.dto.UserDto;

import com.the15373.dochub.pojo.Relationship;
import com.the15373.dochub.pojo.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.hibernate.SessionFactory;

@Repository
public class RelationshipDaoImpl implements RelationshipDao {

	
	@Resource
	SessionFactory sessionFactory;
	@Override
	public Map<String, String> addRelationship(Relationship relationship) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().persist(relationship);
		Map<String,String> map=new HashMap<String,String>();
		map.put("status", 1+"");
		return map;
	}
	@Override
	public Map<String, String> deleRelationship(Relationship relation) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(relation);
		Map<String,String> map=new HashMap<String,String>();
		map.put("status", 1+"");
		return map;
	}
	@Override
	public List<UserDto> getByUserid(Long userid) {
		// TODO Auto-generated method stub
		 String hql="from User u where u.userid in (select friendid from Relationship r where r.user.userid=:userid)";
	     List<User> list= sessionFactory.getCurrentSession().createQuery(hql,User.class).setParameter("userid", userid).getResultList();
		 List<UserDto> list1 = new ArrayList<>();
		 for(User e : list) {
				list1.add(e.toDto());
			}
			return list1;	
	}
	@Override
	public Relationship getByUseridAndFriendid(long friendid, long userid) {
		String hql = "from Relationship r where r.user.userid =:userid and r.friendid =:friendid";
		return sessionFactory.getCurrentSession().createQuery(hql, Relationship.class).setParameter("userid", userid).setParameter("friendid", friendid).uniqueResult();
	}


}
