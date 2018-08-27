package com.the15373.dochub.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.the15373.dochub.dto.*;
import com.the15373.dochub.pojo.Excel;
import com.the15373.dochub.util.DateUtil;
import com.the15373.dochub.util.ExcelFileTools;
import com.the15373.dochub.util.UpUtils;
import com.the15373.dochub.service.UserService;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.the15373.dochub.service.ExcelService;

/**
 * excel控制类
 * @author afang
 *
 * @date 2018年6月25日
 */


@Controller
@RequestMapping("/excels")
public class ExcelController {
	static {
		System.out.println("@static{ExcelController}");
	}
	@Resource
	private ExcelService excelService;
	@Resource
	private UserService userService;

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
			UserDto user = (UserDto) request.getSession().getAttribute("user");
			ArrayList<String[]> data = excelService.excelToTable(baseDir, Long.parseLong(excelId), user);
			if(data == null){
				ErrorResponse res = new ErrorResponse();
				res.setStatus("003003");
				res.setErrors("该excel不存在");
				return res;
			}
			else{
				String path = data.get(data.size() - 1)[0];
				String tmpPath = data.get(data.size() - 1)[1];
				data.remove(data.size() - 1);
				ExcelFileTools.addExcel(new File(path), new File(tmpPath), data, data.size());
				String str = ExcelFileTools.Excel2Table(tmpPath, excelId + "");
				DefaultResponse<String> res = new DefaultResponse<>();
				res.setData(str);
				res.setStatus("1");
				return res;
			}

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
			UserDto user = (UserDto) request.getSession(false).getAttribute("user");
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
			UserDto user = (UserDto) request.getSession(false).getAttribute("user");
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
			String baseDir = request.getServletContext().getInitParameter("baseDir");
			UserDto user = (UserDto) request.getSession(false).getAttribute("user");
			DefaultResponse<List<ExcelDto>> res = (DefaultResponse<List<ExcelDto>>)excelService.getExcelFromFriends(user);
			for(ExcelDto e : res.getData()){
				System.out.println(e.toString());
				if(e.getHead() == null || e.getHead() == ""){
					String table = ExcelFileTools.Excel2Table(
							baseDir + e.getPath(), e.getExcelid() + "");
					System.out.print(table);
					e.setHead(table);
				}
			}

			return res;
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
			UserDto user = (UserDto) request.getSession(false).getAttribute("user");
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
	@Transactional
	@ResponseBody
	@RequestMapping("/uploadExcel")
	public Map<String, String> uploadExcel(MultipartFile filedata, String startdate, String starttime,
										   String deadlinedate, String deadlinetime, String description,
										   HttpServletRequest request){

		String baseDir = request.getServletContext().getInitParameter("baseDir");
		UserDto user = (UserDto) request.getSession(false).getAttribute("user");
		try {
			Excel excel = new Excel();
			excel.setStarttime(DateUtil.sdf.parse(startdate + " " + starttime + ":00"));
			excel.setDeadline(DateUtil.sdf.parse(deadlinedate + " " + deadlinetime + ":00"));
			excel.setDescription(description);
			System.out.println(filedata.getOriginalFilename());
			String tmpPath = UpUtils.getSrc(filedata, request);
			excel.setFilename(filedata.getOriginalFilename());
			String path = "excelfiles/" + user.getUserid() + "/" + excel.getFilename();
			excel.setPath(path);
			File file = new File(baseDir + "excelfiles/" + user.getUserid());
			if(!file.exists()) {
				file.mkdirs();
			}
			FileInputStream is = new FileInputStream(new File(tmpPath));
			FileOutputStream os = new FileOutputStream(new File(baseDir + path));
			int len;
			byte[] bytes = new byte[2048];

			while((len = is.read(bytes)) != -1) {
				os.write(bytes, 0, len);
			}
			is.close();
			os.close();
			if(new File(baseDir + path).exists()) {
				Long id = excelService.uploadExcel(tmpPath, baseDir, excel, user);
				excel.setExcelid(id);
				String table = ExcelFileTools.Excel2Table(
						baseDir + excel.getPath(), id + "");
				excel.setHead(table);
				excelService.update(excel);
				Map<String, String> res = new HashedMap<>();
				res.put("status", 1 + "");
				return res;
			}
			else {
				Map<String, String> res = new HashedMap<>();
				res.put("status", 2 + "");
				return res;
			}
//			return excelService.uploadExcel(tmpPath, baseDir, excel, user);
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
	 * 下载用户上传的excel文件
	 * @param excelid excel文件的id
	 * @param request
	 * @return 
	 */
	@RequestMapping("/downloadExcel")
	public ResponseEntity<byte[]> downloadExcel(String excelid, HttpServletRequest request){
		try {
			UserDto user = (UserDto)request.getSession().getAttribute("user");
			String baseDir = request.getSession().getServletContext().getInitParameter("baseDir");
			if(!(baseDir.endsWith("\\") || baseDir.endsWith("/"))) {
				baseDir += File.separator;
			}
			ExcelDto e = excelService.getById(user, Long.parseLong(excelid));
			ArrayList<String[]> data = excelService.downloadExcel(baseDir, Long.parseLong(excelid), user);
			String path = data.get(data.size() - 1)[0];
			String tmpPath = data.get(data.size() - 1)[1];
			String fileName = data.get(data.size() - 1)[2];
			data.remove(data.size() - 1);
			System.out.println(data.size());
			for(String[] strs : data){
				for(String str : strs){
					System.out.print(str + " ");
				}
				System.out.println();
			}

			System.out.print(data.size());

			ExcelFileTools.addExcel(new File(path), new File(tmpPath), data, data.size());

			File file = new File(tmpPath);
			HttpHeaders headers = new HttpHeaders();
			//下载显示的文件名，解决中文名称乱码问题
			String downloadFielName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
			//通知浏览器以attachment（下载方式）打开图片
			headers.setContentDispositionFormData("attachment", downloadFielName);
			//application/octet-stream ： 二进制流数据（最常见的文件下载）。
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}
