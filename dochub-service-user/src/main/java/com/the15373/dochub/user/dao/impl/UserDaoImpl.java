package com.the15373.dochub.user.dao.impl;

import com.the15373.dochub.dao.UserDao;
import com.the15373.dochub.dto.UserDto;
import com.the15373.dochub.pojo.User;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author afang
 *
 * @date 2018年6月25日
 */

@Repository
public class UserDaoImpl implements UserDao {

	@Resource
	SessionFactory sessionFactory;
	
	@Override
	public User getByAccount(String account) {
		String hql = "from User u where u.account =:account";
		return sessionFactory.getCurrentSession().createQuery(hql, User.class).setParameter("account", account).uniqueResult();
	}

	@Override
	public void regist(User user) {
		sessionFactory.getCurrentSession().persist(user);
	}

	@Override
	public List<UserDto> getFriends(Long userid) {
		String hql = "from User u where u.userid in(select friendid from Relationship r where r.user.userid =:userid)";
		List<User> users = sessionFactory.getCurrentSession().createQuery(hql, User.class)
				.setParameter("userid", userid).getResultList();
		
		List<UserDto> res = new  ArrayList<>();
		for(User u : users) {
			res.add(u.toDto());
		}
		return res;
	}

	@Override
	public void update(User user) {
		sessionFactory.getCurrentSession().update(user);
	}

}
