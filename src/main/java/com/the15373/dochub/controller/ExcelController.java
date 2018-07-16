package com.the15373.dochub.controller;

import java.io.File;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.the15373.dochub.dto.AbstractResponse;
import com.the15373.dochub.dto.ErrorResponse;
import com.the15373.dochub.pojo.Excel;
import com.the15373.dochub.pojo.User;
import com.the15373.dochub.service.ExcelService;
import com.the15373.dochub.util.DateUtil;
import com.the15373.dochub.util.UpUtils;

/**
 * excel控制类
 * @author afang
 *
 * @date 2018年6月25日
 */


@Controller
@RequestMapping("/excels")
public class ExcelController {
	@Resource
	private ExcelService excelService;
	
	public Map<String, String> updateExcel(MultipartFile filedata, String startdate, String starttime, 
			String deadlinedate, String deadlinetime, String description, HttpServletRequest request){
		
		return null;
	}
	/**
	 * 将excel文件转化为HTML里的table
	 * @param excelId excel文件的id
	 * @param request 
	 * @return String HTML的table字符串
	 */
	@ResponseBody
	@RequestMapping("/excelToTable")
	public AbstractResponse excelToTable(String excelId, HttpServletRequest request) {
		try {
			String baseDir = request.getSession().getServletContext().getInitParameter("baseDir");
			User user = (User)request.getSession().getAttribute("user");
			return excelService.excelToTable(baseDir, Long.parseLong(excelId), user);
		}catch (NumberFormatException e) {
			ErrorResponse res = new ErrorResponse();
			res.setStatus("003003");
			res.setErrors(e.getMessage());
			return res;
		}
		catch (Exception e) {
			ErrorResponse res = new ErrorResponse();
			res.setStatus("003002");
			res.setErrors(e.getMessage());
			return res;
		}
	}
	
	/**
	 * 获取excel表格表头
	 * @param excelId excel表格id
	 * @param request
	 * @return String HTML的table字符串::c::r，以::隔开第二个字符串表示行数，第三个字符串表示列数
	 */
	@ResponseBody
	@RequestMapping("/getExcelHead")
	public AbstractResponse getExcelHead(String excelId, HttpServletRequest request) {
		try {
			User user = (User)request.getSession(false).getAttribute("user");
			String baseDir = request.getServletContext().getInitParameter("baseDir");
			return excelService.getExcelHead(baseDir, Long.parseLong(excelId), user);
		}catch (NumberFormatException e) {
			e.printStackTrace();
			ErrorResponse res = new ErrorResponse();
			res.setStatus("003003");
			res.setErrors(e.getMessage());
			return res;
		}
		catch (Exception e) {
			e.printStackTrace();
			ErrorResponse res = new ErrorResponse();
			res.setStatus("003002");
			res.setErrors(e.getMessage());
			return res;
		}
	}
	
	
	/**
	 * 删除excel
	 * @param excelId 
	 * @param request
	 * @return 返回请求状态信息，参见状态码表
	 */
	@ResponseBody
	@RequestMapping("/deleteExcel")
	public Map<String, String> deleteExcel(String excelId, HttpServletRequest request){
		Map<String, String> res = new HashMap<String, String>();
		try{
			User user = (User)request.getSession(false).getAttribute("user");
			return excelService.deleteExel(Long.parseLong(excelId), user);
		} catch (Exception e){
			e.printStackTrace();
			res.put("status", "003002");
			res.put("errors", e.getMessage());
			return res;
		}
	}
	
	/**
	 * 通过文件名获取excel
	 * @param fileName excel表格文件名
	 * @return [{"user":"", "excelid":"", "filename":"", "description":"", "starttime":"", "deadline":"", "mysubmit":""}, ....]
	 */
	@ResponseBody
	@RequestMapping("/getByFileName")
	public AbstractResponse getByFileName(String fileName){
		try {
			return excelService.getByFileName(fileName);
		}catch (Exception e) {
			e.printStackTrace();
			ErrorResponse res = new ErrorResponse();
			res.setStatus("003002");
			res.setErrors(e.getMessage());
			return res;
		}
	}
	
	/**
	 * 通过用户名获取excel
	 * @param userName
	 * @return [{"user":"", "excelid":"", "filename":"", "description":"", "starttime":"", "deadline":"", "mysubmit":""}, ....]
	 */
	@ResponseBody
	@RequestMapping("/getByUserName")
	public AbstractResponse getByUserName(String userName){
		try {
			return excelService.getByUserName(userName);
		}catch (Exception e) {
			e.printStackTrace();
			ErrorResponse res = new ErrorResponse();
			res.setStatus("003002");
			res.setErrors(e.getMessage());
			return res;
		}
	}
	
	/**
	 * 获取关注的用户上传的excel
	 * @param request
	 * @return [{"user":"", "excelid":"", "filename":"", "description":"", "starttime":"", "deadline":"", "mysubmit":""}, ....]
	 */
	@ResponseBody
	@RequestMapping("/getExcelFromFriends")
	public AbstractResponse getExcelFromFriends(HttpServletRequest request){
		try {
			User user = (User)request.getSession(false).getAttribute("user");
			return excelService.getExcelFromFriends(user);
		}catch (Exception e) {
			e.printStackTrace();
			ErrorResponse res = new ErrorResponse();
			res.setStatus("003002");
			res.setErrors(e.getMessage());
			return res;
		}
	}
	
	/**
	 * 获取用户上传的excel
	 * @param request
	 * @return [{"user":"", "excelid":"", "filename":"", "description":"", "starttime":"", "deadline":"", "excelrecords":""}, ....]
	 */
	@ResponseBody
	@RequestMapping("/getMyExcels")
	public AbstractResponse getMyExcel(HttpServletRequest request) {
		try {
			User user = (User)request.getSession(false).getAttribute("user");
			return excelService.getByUser(user); 
		}catch (Exception e) {
			e.printStackTrace();
			ErrorResponse res = new ErrorResponse();
			res.setStatus("003002");
			res.setErrors(e.getMessage());
			return res;
		}
	}
	
	/**
	 * 上传excel文件
	 * @param filedata 文件 
	 * @param startdate 开始日期 2018-06-28
	 * @param starttime 开始时间 00:00
	 * @param deadlinedate 结束日期 2018-06-28
	 * @param deadlinetime 结束时间 00:00
	 * @param description 描述
	 * @param request
	 * @return 返回请求状态码
	 */
	@ResponseBody
	@RequestMapping("/uploadExcel")
	public Map<String, String> uploadExcel(MultipartFile filedata, String startdate, String starttime, 
			String deadlinedate, String deadlinetime, String description, HttpServletRequest request){
		
		String baseDir = request.getServletContext().getInitParameter("baseDir");
		User user = (User)request.getSession(false).getAttribute("user");
		try {
			Excel excel = new Excel();
			excel.setStarttime(DateUtil.sdf.parse(startdate + " " + starttime + ":00"));
			excel.setDeadline(DateUtil.sdf.parse(deadlinedate + " " + deadlinetime + ":00"));
			excel.setDescription(description);
			System.out.println(filedata.getOriginalFilename());
			excel.setUser(user);
			String tmpPath = UpUtils.getSrc(filedata, request);
			excel.setFilename(filedata.getOriginalFilename());
			
			return excelService.uploadExcel(tmpPath, baseDir, excel);
			
		}catch (ParseException e) {
			e.printStackTrace();
			Map<String, String> res = new HashedMap<>();
			res.put("status", "003006");
			res.put("errors", e.getMessage());
			return res;
		}
		catch (Exception e) {
			e.printStackTrace();
			Map<String, String> res = new HashedMap<>();
			res.put("status", "003002");
			res.put("errors", e.getMessage());
			return res;
		}
	}
	
	/**
	 * 下载用户上传到excel文件
	 * @param excelid excel文件的id
	 * @param request
	 * @return 
	 */
	@RequestMapping("/downloadExcel")
	public ResponseEntity<byte[]> downloadExcel(String excelid, HttpServletRequest request){
		try {
			User user = (User)request.getSession().getAttribute("user");
			String baseDir = request.getSession().getServletContext().getInitParameter("baseDir");
			if(!(baseDir.endsWith("\\") || baseDir.endsWith("/"))) {
				baseDir += File.separator;
			}
			return excelService.downloadExcel(baseDir, Long.parseLong(excelid), user);
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}
