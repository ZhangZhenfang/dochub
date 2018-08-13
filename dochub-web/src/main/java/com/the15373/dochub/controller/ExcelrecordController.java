package com.the15373.dochub.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.the15373.dochub.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.the15373.dochub.dto.AbstractResponse;
import com.the15373.dochub.dto.ErrorResponse;
import com.the15373.dochub.pojo.User;
import com.the15373.dochub.service.ExcelrecordService;

/**
 * excelrecord控制类
 * @author afang
 *
 * @date 2018年6月26日
 */

@Controller
@RequestMapping("/excelrecords")
public class ExcelrecordController {
	
	@Resource
	private ExcelrecordService excelrecordService;
	
	/**
	 * 添加一条excel表格记录（一行）
	 * @param record json格式字符串,{"0":"", "1":"", "2":"", ......}
	 * @param excelId 字符串数字
	 * @param request 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/insertOrUpdateExcelrecord")
	public Map<String, String> insertOrUpdateExcelrecord(String record, String excelId, HttpServletRequest request){
		try {
			System.out.println(record);
			System.out.println(excelId);
			UserDto user = (UserDto) request.getSession(false).getAttribute("user");
			return excelrecordService.addExcelrecord(user, Long.parseLong(excelId), record);
		}catch (NumberFormatException e) {
			e.printStackTrace();
			Map<String, String> res = new HashMap<>();
			res.put("status", "004003");
			res.put("errors", e.getMessage());
			return res;
		}
		catch (Exception e) {
			e.printStackTrace();
			Map<String, String> res = new HashMap<>();
			res.put("status", "004002");
			res.put("errors", e.getMessage());
			return res;
		}
	}
	
	/**
	 * 通过用户名和excel表格id获取excel表格记录
	 * @param excelId excel表格的id
	 * @param request 
	 * @return {"excel":"", "record":"", "user":"", "time":""}
	 */
	@ResponseBody
	@RequestMapping("/getExcelrecordByUseridAndExcelId")
	public AbstractResponse getExcelrecordByUseridAndExcelId(String excelId, HttpServletRequest request){
		try {
			UserDto user = (UserDto) request.getSession().getAttribute("user");
			return excelrecordService.getExcelrecordByUseridAndExcelId(Long.parseLong(excelId), user);
		}catch (NumberFormatException e) {
			e.printStackTrace();
			ErrorResponse res = new ErrorResponse();
			res.setStatus("004003");
			res.setErrors(e.getMessage());
			return res;
		}
		catch (Exception e) {
			e.printStackTrace();
			ErrorResponse res = new ErrorResponse();
			res.setStatus("004002");
			res.setErrors(e.getMessage());
			return res;
		}
	}
	
	/**
	 * 根据excel表格的id查询excel表格的记录
	 * @param excelId excel表格的id
	 * @param request
	 * @return [{"excel":"", "record":"", "user":"", "time":""}, ......]
	 */
	@ResponseBody
	@RequestMapping("/getExcelreocrdsByExcelId")
	public AbstractResponse getExcelreocrdsByExcelId(String excelId, HttpServletRequest request){
		try {
			UserDto user = (UserDto) request.getSession().getAttribute("user");
			return excelrecordService.getExcelreocrdsByExcelId(Long.parseLong(excelId), user);
		}catch (NumberFormatException e) {
			e.printStackTrace();
			ErrorResponse res = new ErrorResponse();
			res.setStatus("004003");
			res.setErrors(e.getMessage());
			return res;
		}
		catch (Exception e) {
			e.printStackTrace();
			ErrorResponse res = new ErrorResponse();
			res.setStatus("004002");
			res.setErrors(e.getMessage());
			return res;
		}
	}
	
	
}
