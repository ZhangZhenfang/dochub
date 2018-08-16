package com.the15373.dochub.notice.service.impl;

import com.the15373.dochub.dao.FileDao;

import com.the15373.dochub.dao.NoticeDao;
import com.the15373.dochub.dto.*;
import com.the15373.dochub.pojo.File;
import com.the15373.dochub.pojo.Notice;
import com.the15373.dochub.pojo.User;
import com.the15373.dochub.service.FileService;
import com.the15373.dochub.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件上传业务类
 * @author afang
 *
 * @date 2018年6月27日
 */

@Service
@Transactional
public class FileServiceImpl implements FileService {
	@Resource
	private UserService userService;
	@Resource
	private FileDao fileDao;
	@Resource
	private NoticeDao noticeDao;
	@Override
	public Map<String, String> uploadFile(String baseDir, String tmpPath, String fileName, long noticeid, UserDto user) throws IOException {
		Notice notice = noticeDao.getByNoticeId(noticeid);
		boolean f;
		if(notice != null) {
			File file = fileDao.getByNoticeIdAndUserid(noticeid, Long.parseLong(user.getUserid()));
			if(file == null) {
				file = new File();
				f = true;
			}
			else {
				java.io.File fi = new java.io.File(baseDir + file.getPath());
				if(fi.exists()) {
					fi.delete();
				}
				f = false;
			}
			UserDto u = userService.getByAccount(user.getAccount());
			User us = new User();
			us.setUserid(Long.parseLong(u.getUserid()));
			file.setFilename(fileName);
			file.setTime(new Date());
			file.setUser(us);
			file.setNotice(notice);
			file.setPath(notice.getPath() + java.io.File.separator + file.getFilename());
			
			FileInputStream fis = new FileInputStream(new java.io.File(tmpPath));
			java.io.File fil = new java.io.File(baseDir + notice.getPath());
			if(!fil.exists()) {
				fil.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(new java.io.File(baseDir + file.getPath()));
			
			byte[] bytes = new byte[2048];
			int len;
			while((len = fis.read(bytes)) != -1) {
				fos.write(bytes, 0, len);
			}
			fis.close();
			fos.close();
			if(f == true) {
				fileDao.save(file);
			}
			else {
				fileDao.update(file);
			}
			
			Map<String, String> res = new HashMap<>();
			res.put("status", "1");
			return res;
		}
		else {
			Map<String, String> res = new HashMap<>();
			res.put("status", "006003");
			return res;
		}
	}
	@Override
	public String downloadFile(String baseDir, long fileid, UserDto user) throws Exception {
		File file = fileDao.getById(fileid, Long.parseLong(user.getUserid()));
		if(file != null) {

			return baseDir + file.getPath() + "~" + file.getFilename();

		}
		else {
			return null;
		}

	}
	@Override
	public AbstractResponse getFilesByNoticeid(long noticeid, UserDto user) {
		Notice notice = noticeDao.getByUserIdAndId(Long.parseLong(user.getUserid()), noticeid);
		if(notice != null) {
			List<FileDto> data = fileDao.getByNoticeId(noticeid);
			DefaultResponse<List<FileDto>> res = new DefaultResponse<>();
			res.setData(data);
			res.setStatus("1");
			return res;
		}
		else {
			ErrorResponse res = new ErrorResponse();
			res.setStatus("006004");
			res.setErrors("获取files时notice权限异常");
			return res;
		}
	}
	@Override
	public AbstractResponse getFilesByNoticeidAndUserid(long noticeid, UserDto user) {
		File f = fileDao.getByNoticeIdAndUserid(noticeid, Long.parseLong(user.getUserid()));
//		System.out.println(f.getFileid());
		DefaultResponse<FileDto> res = new DefaultResponse<>();
//		System.out.println(f.toDto().getFileid());
		if(f != null) {
			res.setData(f.toDto());
		}
		else {
			res.setData(null);
		}
		res.setStatus("1");
		return res;
	}
}
