package com.the15373.dochub.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
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
import com.the15373.dochub.dto.NoticeDto;
import com.the15373.dochub.pojo.Notice;
import com.the15373.dochub.pojo.User;
import com.the15373.dochub.service.NoticeService;
import com.the15373.dochub.util.Compress;
import com.the15373.dochub.util.DateUtil;

/**
 * notice业务类
 * @author afang
 *
 * @date 2018年6月27日
 */

@Service
@Transactional
public class NoticeServiceImpl implements NoticeService {

	@Resource
	private NoticeDao noticeDao;
	@Resource
	private FileDao fileDao;
	
	@Override
	public Map<String, String> newNotice(Notice notice) {
		
		if(notice.getDeadline() == null || notice.getStarttime() == null || notice.getDescription() == null) {
			Map<String, String> res = new HashMap<>();
			res.put("status", "005003");
			res.put("errors", "notice要求字段为空");
			return res;
		}
		else {
			Map<String, String> res = new HashMap<>();
			res.put("status", "1");
			noticeDao.save(notice);
			notice.setPath("noticedata/" + notice.getUser().getUserid() + "/" + notice.getNoticeid());
			noticeDao.update(notice);
			return res;
		}
	}
	
	

	@Override
	public AbstractResponse getMyNotice(User user) {
		DefaultResponse<List<NoticeDto>> res = new DefaultResponse<>();
		
		List<Notice> list = noticeDao.getByUserId(user.getUserid());
		List<NoticeDto> data = new ArrayList<>();
		NoticeDto dto;
		for(Notice n : list) {
			dto = n.toDto();
			dto.setFiles(fileDao.getByNoticeId(n.getNoticeid()));
			data.add(dto);
		}
		res.setData(data);
		res.setStatus("1");
		return res;
	}

	@Override
	public AbstractResponse getNoticesFromFriends(User user) {
		DefaultResponse<List<NoticeDto>> res = new DefaultResponse<>();
		List<Notice> list = noticeDao.getNoticesFromFriends(user.getUserid());
		NoticeDto dto;
		List<NoticeDto> data = new ArrayList<>();
		for(Notice n : list) {
			dto = n.toDto();
			com.the15373.dochub.pojo.File file = fileDao.getByNoticeIdAndUserid(n.getNoticeid(), user.getUserid());
			if(file == null) {
				dto.setMysubmt(null);
			}
			else {
				System.out.println(file.toDto().getFileid());
				dto.setMysubmt(file.toDto());
			}
			
//			fileDao.getByNoticeIdAndUserid(n.getNoticeid(), user.getUserid()).toDto();
			data.add(dto);
		}
		
		
		res.setData(data);
		res.setStatus("1");
		return res;
	}

	@Override
	public Map<String, String> updateNotice(Notice notice, User user) {
		Notice n = noticeDao.getByUserIdAndId(user.getUserid(), notice.getNoticeid());
		if(n != null) {
			n.setDeadline(notice.getDeadline());
			n.setStarttime(notice.getStarttime());
			n.setDescription(notice.getDescription());
			n.setType(notice.getType());
			n.setNotice(notice.getNotice());
			notice = null;
			noticeDao.update(n);
			Map<String, String> res = new HashMap<>();
			res.put("status", "1");
			return res;
		}
		else {
			Map<String, String> res = new HashMap<>();
			res.put("005004", "notice更新权限异常");
			return res;
		}
	}



	
	

	@Override
	public ResponseEntity<byte[]> downloadNotice(String baseDir, long noticeid, User user) throws Exception{
		Notice notice = noticeDao.getByUserIdAndId(user.getUserid(), noticeid);
		if(notice != null) {
			try {
				String dir = baseDir + notice.getPath();
				if(!(dir.endsWith("\\") || dir.endsWith("/"))) {
					dir += File.separator;
				}
				File file = new File(dir);
				if(!file.exists()) {
					file.mkdirs();
				}
//				file = null;
				file = new File(dir + ".info");
				FileOutputStream os = new FileOutputStream(file);
				os.write(("compressed @ " + DateUtil.sdf.format(new Date()) + " by www.the15373.com").getBytes());
				os.close();
//				file = null;
				Compress compress = new Compress(dir + notice.getNotice()+ ".zip");
				compress.compressExe(baseDir + notice.getPath());
				String path = dir + notice.getNotice()+ ".zip";
				System.out.println(path);
				if(!new File(path).exists()) {
					throw new FileNotFoundException();
				}
				else {
					file = new File(path);
					HttpHeaders headers = new HttpHeaders();  
					//下载显示的文件名，解决中文名称乱码问题  
					String downloadFielName = new String((notice.getNotice()+ ".zip").getBytes("UTF-8"),"iso-8859-1");
					//通知浏览器以attachment（下载方式）打开图片
					headers.setContentDispositionFormData("attachment", downloadFielName); 
					//application/octet-stream ： 二进制流数据（最常见的文件下载）。
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
				}
	          
			}catch (Exception e) {
				throw e;
			}
		}
		else {
			return null;
		}
	}



	@Override
	public Map<String, String> deleteNotice(long noticeid, User user) {
		Notice notice = noticeDao.getByUserIdAndId(user.getUserid(), noticeid);
		if(notice != null) {
			noticeDao.delete(notice);
			Map<String, String> res =  new HashMap<>();
			res.put("status", "1");
			return res;
		}
		else {
			Map<String, String> res =  new HashMap<>();
			res.put("status", "005005");
			res.put("errors", "notice删除权限异常");
			return res;
		}
	}
	
}
