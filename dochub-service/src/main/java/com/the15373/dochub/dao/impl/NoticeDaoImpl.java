package com.the15373.dochub.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.the15373.dochub.dao.NoticeDao;
import com.the15373.dochub.pojo.Notice;

/**
 * notice数据库访问对象
 * @author afang
 *
 * @date 2018年6月27日
 */

@Repository
public class NoticeDaoImpl implements NoticeDao{

	@Resource
	private SessionFactory sessionFactory;
	
	@Override
	public void save(Notice notice) {
		sessionFactory.getCurrentSession().save(notice);
	}

	@Override
	public void update(Notice notice) {
		sessionFactory.getCurrentSession().update(notice);
	}

	@Override
	public List<Notice> getByUserId(Long userid) {
		String hql = "from Notice n where n.user.userid =:userid";
		List<Notice> list = sessionFactory.getCurrentSession().createQuery(hql, Notice.class)
				.setParameter("userid", userid).getResultList();
		return list;
	}

	@Override
	public List<Notice> getNoticesFromFriends(Long userid) {
		String hql = "from Notice n where n.user.userid in(select friendid from Relationship r where r.user.userid =:userid)"; 
		List<Notice> list = sessionFactory.getCurrentSession().createQuery(hql, Notice.class)
				.setParameter("userid", userid).getResultList();
		return list;
	}

	@Override
	public Notice getByUserIdAndId(Long userid, Long noticeid) {
		String hql = "from Notice n where n.noticeid =:noticeid and n.user.userid =:userid";
		return sessionFactory.getCurrentSession().createQuery(hql, Notice.class)
			.setParameter("noticeid", noticeid).setParameter("userid", userid).uniqueResult();
	}

	@Override
	public void delete(Notice notice) {
		sessionFactory.getCurrentSession().delete(notice);
	}

	@Override
	public Notice getByNoticeId(long noticeid) {
		String hql = "from Notice n where n.noticeid =:noticeid";
		return sessionFactory.getCurrentSession().createQuery(hql, Notice.class)
				.setParameter("noticeid", noticeid).uniqueResult();
	}
	
}
