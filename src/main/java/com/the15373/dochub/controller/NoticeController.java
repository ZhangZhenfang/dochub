package com.the15373.dochub.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.the15373.dochub.dto.AbstractResponse;
import com.the15373.dochub.dto.ErrorResponse;
import com.the15373.dochub.pojo.Notice;
import com.the15373.dochub.pojo.User;
import com.the15373.dochub.service.NoticeService;
import com.the15373.dochub.util.DateUtil;

/**
 * 公告Notice控制类
 * @author afang
 *
 * @date 2018年6月27日
 */

@Controller
@RequestMapping("/notices")
public class NoticeController {

	@Resource
	private NoticeService noticeService;
	
	/**
	 * 新建一个公告notice
	 * @param notice 公告名
	 * @param startdate 开始日期 2018-06-28
	 * @param starttime 开始时间 00:00
	 * @param deadlinedate 结束日期 2018-06-28
	 * @param deadlinetime 结束时间 00:00
	 * @param description 描述
	 * @param type 类型 int
	 * @param request
	 * @return 请求状态码
	 */
	@ResponseBody
	@RequestMapping("/newNotice")
	public Map<String, String> newNotice(String notice, String startdate,
			String starttime, String deadlinedate, 
			String deadlinetime, String description,
			String type, HttpServletRequest request) {
		try {
			User user = (User)request.getSession().getAttribute("user");
			Notice n = new Notice();
			if(type == null) {
				type = 1 + "";
			}
			n.setStarttime(DateUtil.sdf.parse(startdate + " " + starttime + ":00"));
			n.setDeadline(DateUtil.sdf.parse(deadlinedate + " " + deadlinetime + ":00"));	
			n.setDescription(description);
			n.setType(Integer.parseInt(type));
			n.setUser(user);
			n.setNotice(notice);
			
			return noticeService.newNotice(n);
			
		}catch (Exception e) {
			e.printStackTrace();
			Map<String, String> res = new HashMap<>();
			res.put("status", "005002");
			res.put("errors", e.getMessage());
			return res;
		}
	}
	
	/**
	 * 获取用户发布的notice
	 * @param request
	 * @return [{"notice":"", "noticeid":"", "starttime":"", "deadline":"", "mysubmit":"", "files":""}, ......]
	 */
	@ResponseBody
	@RequestMapping("/getMyNotice")
	public AbstractResponse getMyNotice(HttpServletRequest request) {
		try {
			User user = (User)request.getSession().getAttribute("user");
			return noticeService.getMyNotice(user);
			
		}catch (Exception e) {
			e.printStackTrace();
			ErrorResponse res = new ErrorResponse();
			res.setStatus("005002");
			res.setErrors(e.getMessage());
			return res;
		}
	}
	
	/**
	 * 获取用户关注的人发布的公告notice
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getNoticesFromFriends")
	public AbstractResponse getNoticesFromFriends(HttpServletRequest request) {
		try {
			User user = (User)request.getSession().getAttribute("user");
			return noticeService.getNoticesFromFriends(user);
		}catch (Exception e) {
			e.printStackTrace();
			ErrorResponse res = new ErrorResponse();
			res.setStatus("005002");
			res.setErrors(e.getMessage());
			return res;
		}
	}

	/**
	 * 更新用户发布的notice
	 * @param notice 公告名
	 * @param startdate 开始日期 2018-06-28
	 * @param starttime 开始时间 00:00
	 * @param deadlinedate 结束日期 2018-06-28
	 * @param deadlinetime 结束时间 00:00
	 * @param description 描述
	 * @param type 类型 int
	 * @param request
	 * @return 请求状态码
	 * 
	 */
	@ResponseBody
	@RequestMapping("/updateNotice")
	public Map<String, String> updateNotice(String noticeid, 
			String notice, String startdate,
			String starttime, String deadlinedate, 
			String deadlinetime, String description,
			String type, HttpServletRequest request){
		
		try {
			User user = (User)request.getSession().getAttribute("user");

			Notice n = new Notice();
			n.setStarttime(DateUtil.sdf.parse(startdate + " " + starttime + ":00"));
			n.setDeadline(DateUtil.sdf.parse(deadlinedate + " " + deadlinetime + ":00"));	
			n.setDescription(description);
			n.setType(Integer.parseInt(type));
			n.setNoticeid(Long.parseLong(noticeid));
			n.setNotice(notice);
			return noticeService.updateNotice(n, user);
		}catch (Exception e) {
			e.printStackTrace();
			Map<String, String> res = new HashMap<>();
			res.put("status", "005002");
			res.put("errors", e.getMessage());
			return res;
		}
	}
	
	/**
	 * 下载notice，将其他用户上传的文件打包下载。
	 * @param noticeid 公告notice的id
	 * @param request
	 * @return 文件
	 */
	@RequestMapping(value="/downloadNotice")
    public ResponseEntity<byte[]> downloadNotice(String noticeid, HttpServletRequest request){
       try {
    	   User user = (User)request.getSession().getAttribute("user");
    	   String baseDir = request.getSession().getServletContext().getInitParameter("baseDir");
    	   if(!(baseDir.endsWith("\\") || baseDir.endsWith("/"))) {
    		   baseDir += File.separator;
    	   }
    	   return noticeService.downloadNotice(baseDir, Long.parseLong(noticeid), user);
       }catch (Exception e) {
    	   e.printStackTrace();
    	   return null;
       }
    }
    
    /**
     * 删除notice
     * @param noticeid 数字
     * @param request
     * @return 请求状态码
     */
    @ResponseBody
    @RequestMapping("/deleteNotice")
    public Map<String, String> deleteNotice(String noticeid, HttpServletRequest request) {
    	try {
    		User user = (User)request.getSession().getAttribute("user");
    		
    		return noticeService.deleteNotice(Long.parseLong(noticeid), user);
    	}catch (Exception e) {
    		Map<String, String> res = new HashMap<>();
    		res.put("status", "005002");
    		res.put("errors", e.getMessage());
    		return res;
    	}
    }
}
