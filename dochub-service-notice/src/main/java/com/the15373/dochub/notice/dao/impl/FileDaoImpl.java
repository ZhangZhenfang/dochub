package com.the15373.dochub.notice.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.the15373.dochub.dto.FileDto;
import com.the15373.dochub.pojo.File;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.the15373.dochub.dao.FileDao;

/**
 * 文件上传数据库访问对象
 * @author afang
 *
 * @date 2018年6月27日
 */

@Repository
public class FileDaoImpl implements FileDao {
	@Resource
	private SessionFactory sessionFactory;

	@Override
	public void save(File file) {
		sessionFactory.getCurrentSession().save(file);
	}

	@Override
	public File getById(long fileid, long userid) {
		String hql = "from File f where f.fileid =:fileid and f.user.userid =:userid";
		return sessionFactory.getCurrentSession().createQuery(hql, File.class)
				.setParameter("fileid", fileid).setParameter("userid", userid).uniqueResult();
	}

	@Override
	public List<FileDto> getByNoticeId(long noticeid) {
		String hql = "from File f where f.notice.noticeid =:noticeid order by f.time desc";
		List<File> files = sessionFactory.getCurrentSession().createQuery(hql, File.class)
				.setParameter("noticeid", noticeid).getResultList();
		List<FileDto> data = new ArrayList<>();
		for(File f : files) {
			data.add(f.toDto());
		}
		return data;
	}

	@Override
	public File getByNoticeIdAndUserid(long noticeid, Long userid) {
		String hql = "from File f where f.notice.noticeid =:noticeid and f.user.userid =:userid";
		return sessionFactory.getCurrentSession().createQuery(hql, File.class)
				.setParameter("noticeid", noticeid).setParameter("userid", userid).uniqueResult();
	}

	@Override
	public void update(File file) {
		sessionFactory.getCurrentSession().update(file);
	}
	
}
