package com.the15373.dochub.service.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.the15373.dochub.dao.FileDao;
import com.the15373.dochub.dao.NoticeDao;
import com.the15373.dochub.dto.AbstractResponse;
import com.the15373.dochub.dto.DefaultResponse;
import com.the15373.dochub.dto.ErrorResponse;
import com.the15373.dochub.dto.FileDto;
import com.the15373.dochub.pojo.File;
import com.the15373.dochub.pojo.Notice;
import com.the15373.dochub.pojo.User;
import com.the15373.dochub.service.FileService;



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
	private FileDao fileDao;
	@Resource
	private NoticeDao noticeDao;
	@Override
	public Map<String, String> uploadFile(String baseDir, String tmpPath, String fileName, long noticeid, User user) throws IOException {
		Notice notice = noticeDao.getByUserId(noticeid);
		boolean f;
		if(notice != null) {
			File file = fileDao.getByNoticeIdAndUserid(noticeid, user.getUserid());
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
			file.setFilename(fileName);
			file.setTime(new Date());
			file.setUser(user);
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
	public ResponseEntity<byte[]> downloadFile(String baseDir, long fileid, User user) throws Exception {
		File file = fileDao.getById(fileid, user.getUserid());
		if(file != null) {
			java.io.File f = new java.io.File(baseDir + file.getPath());
			HttpHeaders headers = new HttpHeaders();  
			//下载显示的文件名，解决中文名称乱码问题  
			String downloadFielName = new String(file.getFilename().getBytes("UTF-8"),"iso-8859-1");
			//通知浏览器以attachment（下载方式）打开图片
			headers.setContentDispositionFormData("attachment", downloadFielName); 
			//application/octet-stream ： 二进制流数据（最常见的文件下载）。
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(f), headers, HttpStatus.CREATED);
		}
		else {
			return null;
		}

	}
	@Override
	public AbstractResponse getFilesByNoticeid(long noticeid, User user) {
		Notice notice = noticeDao.getByUserIdAndId(user.getUserid(), noticeid);
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
	public AbstractResponse getFilesByNoticeidAndUserid(long noticeid, User user) {
		File f = fileDao.getByNoticeIdAndUserid(noticeid, user.getUserid());
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
