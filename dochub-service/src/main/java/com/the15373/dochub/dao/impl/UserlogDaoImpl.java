package com.the15373.dochub.dao.impl;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.the15373.dochub.dao.UserlogDao;
import com.the15373.dochub.pojo.Userlog;

/**
 * 用户登陆记录数据库访问对象
 * @author afang
 *
 * @date 2018年6月27日
 */

@Repository
public class UserlogDaoImpl implements UserlogDao{

	@Resource
	private SessionFactory sessionFactory;

	@Override
	public void save(Userlog userlog) {
		sessionFactory.getCurrentSession().save(userlog);
	}
}
