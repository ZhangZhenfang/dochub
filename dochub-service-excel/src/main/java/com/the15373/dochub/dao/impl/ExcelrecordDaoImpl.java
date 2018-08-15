package com.the15373.dochub.dao.impl;

import com.the15373.dochub.dao.ExcelrecordDao;
import com.the15373.dochub.dto.ExcelrecordDto;
import com.the15373.dochub.pojo.Excelrecord;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * excel表格记录数据库访问对象
 * @author afang
 *
 * @date 2018年6月26日
 */
@Repository("excelrecordDao")
public class ExcelrecordDaoImpl implements ExcelrecordDao {

	@Resource
	private SessionFactory sessionFactory;
	
	@Override
	public void save(Excelrecord excelrecord) {
		sessionFactory.getCurrentSession().save(excelrecord);
	}

	@Override
	public Excelrecord getByUserIdAndExcelId(long userid, long excelId) {
		String hql = "from Excelrecord er where er.user.userid =:userid and er.excel.excelid =:excelId";
		return sessionFactory.getCurrentSession().createQuery(hql, Excelrecord.class)
				.setParameter("userid", userid)
				.setParameter("excelId", excelId)
				.uniqueResult();
	}

	@Override
	public void update(Excelrecord excelrecord) {
		sessionFactory.getCurrentSession().update(excelrecord);
	}

	@Override
	public ExcelrecordDto getExcelrecordByUseridAndExcelId(long excelId, Long userid) {
		String hql = "from Excelrecord er where er.user.userid =:userid and er.excel.excelid =:excelId";
		Excelrecord er = sessionFactory.getCurrentSession().createQuery(hql, Excelrecord.class)
				.setParameter("userid", userid)
				.setParameter("excelId", excelId)
				.uniqueResult();
		if(er == null) {
			return null;
		}
		return er.toDto();
	}

	@Override
	public List<ExcelrecordDto> getExcelreocrdsByExcelId(long excelId, Long userid) {
		String hql = "from Excelrecord er where er.excel.excelid =:excelId";
		List<Excelrecord> list = sessionFactory.getCurrentSession().createQuery(hql, Excelrecord.class)
				.setParameter("excelId", excelId)
				.getResultList();
		
		List<ExcelrecordDto> res = new ArrayList<>();
		for(Excelrecord er : list) {
			res.add(er.toDto());
		}
		
		return res;
	}
	
}
