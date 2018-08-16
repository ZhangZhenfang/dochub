package com.the15373.dochub.user.service.impl;


import com.the15373.dochub.dao.RelationshipDao;
import com.the15373.dochub.dao.UserDao;
import com.the15373.dochub.dto.UserDto;
import com.the15373.dochub.pojo.User;
import com.the15373.dochub.pojo.Relationship;
import com.the15373.dochub.service.RelationshipService;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RelationshipServiceImpl implements RelationshipService{
	
	@Resource
	RelationshipDao relationshipDao;
    @Resource
    SessionFactory sessionFactory;
    @Resource
    UserDao userDao;


	@Override
	public Relationship getRelationship(UserDto user, Long friendid) {
		// TODO Auto-generated method stub
		String hql="from Relationship r where r.user.userid =:userid and r.friendid =:friendid";
		return sessionFactory.getCurrentSession().createQuery(hql,Relationship.class)
				.setParameter("userid", user.getUserid()).setParameter("friendid", friendid).uniqueResult();
		}

	@Override
	public Map<String, String> deleRelationship(Relationship relation) {
		// TODO Auto-generated method stub
		return relationshipDao.deleRelationship(relation);
	}


	@Override
	public List<UserDto> getByUserid(Long userid) {
		// TODO Auto-generated method stub
		List<UserDto> list=relationshipDao.getByUserid(userid);
		return list;
	}





	@Override
	public Map<String, String> addRelationship(String account, UserDto user) {
		User f = userDao.getByAccount(account);
		if(f != null) {
			User u = userDao.getByAccount(user.getAccount());
			Relationship r = relationshipDao.getByUseridAndFriendid(f.getUserid(), u.getUserid());

			if(r == null) {
				r = new Relationship();
				r.setTime(new Date());
				r.setUser(u);
				r.setFriendid(f.getUserid());
				relationshipDao.addRelationship(r);
				Map<String, String> res = new HashMap<>();
				res.put("status", "1");
				return res;
			}
			else {
				Map<String, String> res = new HashMap<>();
				res.put("status", "2");
				res.put("errors", "已关注该用户");
				return res;
			}
		}
		else {
			Map<String, String> res = new HashMap<>();
			res.put("status", "2");
			res.put("errors", "没有该用户");
			return res;
		}
	}

}
