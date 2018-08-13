package com.the15373.dochub.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.the15373.dochub.dao.ExcelDao;
import com.the15373.dochub.dto.ExcelDto;
import com.the15373.dochub.pojo.Excel;
import com.the15373.dochub.pojo.User;

/**
 * 
 * @author afang
 *
 * @date 2018年6月25日
 */


@Repository
public class ExcelDaoImpl implements ExcelDao{
	@Resource
	private SessionFactory sessionFactory;
	
	@Override
	public List<Excel> getByUser(User user) {
		Set<Excel> set = sessionFactory.getCurrentSession().load(User.class, user.getUserid()).getExcels();
		List<Excel> list = new ArrayList<>();
		for(Excel e : set) {
			list.add(e);
		}
		return list;
	}
	
	@Override
	public void save(Excel excel) {
		sessionFactory.getCurrentSession().persist(excel);
	}

	@Override
	public List<Excel> getExcelFromFriends(User user) {
		String hql = "from Excel e where e.user.userid in (select friendid from Relationship r where r.user.userid =:userid)";
		List<Excel> res = sessionFactory.getCurrentSession().createQuery(hql, Excel.class)
				.setParameter("userid", user.getUserid())
				.getResultList();
		List<ExcelDto> result = new ArrayList<>();
		for(Excel e : res) {
			System.out.println(e.getDescription());
			result.add(e.toDto());
		}
		return res;
	}

	@Override
	public List<ExcelDto> getByUserName(String userName) {
		String hql = "from Excel e where e.user.userid in (select userid from User u where u.name like:userName)";
		List<Excel> res = sessionFactory.getCurrentSession().createQuery(hql, Excel.class)
				.setParameter("userName", "%" + userName + "%")
				.getResultList();
		List<ExcelDto> result = new ArrayList<>();
		for(Excel e : res) {
			System.out.println(e.getDescription());
			result.add(e.toDto());
		}
		return result;
	}

	@Override
	public List<ExcelDto> getByFileName(String fileName) {
		String hql = "from Excel e where e.filename like :fileName";
		List<Excel> res = sessionFactory.getCurrentSession().createQuery(hql, Excel.class)
				.setParameter("fileName", "%" + fileName + "%")
				.getResultList();
		List<ExcelDto> result = new ArrayList<>();
		for(Excel e : res) {
			System.out.println(e.getDescription());
			result.add(e.toDto());
		}
		return result;
	}

	@Override
	public Excel getById(long excelId) {
		String hql = "from Excel e where e.excelid =:excelId";
		return sessionFactory.getCurrentSession().createQuery(hql, Excel.class)
				.setParameter("excelId", excelId)
				.uniqueResult();
	}

	@Override
	public void delete(Excel excel) {
		sessionFactory.getCurrentSession().delete(excel);
	}

	@Override
	public Excel getByUserIDAndId(Long userid, long excelid) {
		String hql = "from Excel e where e.user.userid =:userid and e.excelid =:excelid";
		return sessionFactory.getCurrentSession().createQuery(hql, Excel.class)
				.setParameter("userid", userid).setParameter("excelid", excelid).uniqueResult();
	}

}
