package com.the15373.dochub.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.the15373.dochub.dto.UserDto;
import com.the15373.dochub.dto.AbstractResponse;
import com.the15373.dochub.dto.ErrorResponse;
import com.the15373.dochub.util.UpUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.the15373.dochub.service.FileService;

/**
 * 文件上传控制类
 * @author afang
 *
 * @date 2018年6月27日
 */

@Controller
@RequestMapping("/files")
public class FileController {

	@Resource
	private FileService fileService;
	
	/**
	 * 上传文件到某一个公告
	 * @param fileName
	 * @param md5
	 * @param noticeid 公告notice的id
	 * @param request
	 * @return 状态码
	 */
	@ResponseBody
	@RequestMapping("/uploadFile")
	private Map<String, String> uploadFile(String fileName, String md5, String noticeid,
			HttpServletRequest request){
		try {
//			String tmpPath = UpUtils.getSrc(filedata, request);
			String baseDir = request.getSession().getServletContext().getInitParameter("baseDir");
//			String fileName = filedata.getOriginalFilename();
			UserDto user = (UserDto) request.getSession().getAttribute("user");
			
			return fileService.uploadFile(baseDir, fileName, Long.parseLong(noticeid), user, md5);
		}catch (Exception e) {
			e.printStackTrace();
			Map<String, String> res = new HashMap<>();
			res.put("status", "006002");
			res.put("errors", e.getMessage());
			return res;
		}
	}
	
	/**
	 * 下载文件
	 * @param fileid 文件id
	 * @param request
	 * @return 文件
	 */
	@RequestMapping("/downloadFile")
	public ResponseEntity<byte[]> downloadFile(String fileid, HttpServletRequest request){
		try {
			UserDto user = (UserDto) request.getSession().getAttribute("user");
			String baseDir = request.getSession().getServletContext().getInitParameter("baseDir");
			if(!(baseDir.endsWith("\\") || baseDir.endsWith("/"))) {
				baseDir += "/";
			}
			String res = fileService.downloadFile(baseDir, Long.parseLong(fileid), user);
			String[] tmp = res.split("~");
			java.io.File f = new java.io.File(tmp[0]);
			HttpHeaders headers = new HttpHeaders();
			//下载显示的文件名，解决中文名称乱码问题
			String downloadFielName = new String(tmp[1].getBytes("UTF-8"),"iso-8859-1");
			//通知浏览器以attachment（下载方式）打开图片
			headers.setContentDispositionFormData("attachment", downloadFielName);
			//application/octet-stream ： 二进制流数据（最常见的文件下载）。
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(f), headers, HttpStatus.CREATED);
		}catch (Exception e) {
			return null;
		}
	}
	
	
	/**
	 * 通过notice来获取文件信息
	 * @param noticeid 数字
	 * @param request
	 * @return [{"notice":"", "user":"", "filename":"", "time":""}, ......]
	 */
	@ResponseBody
	@RequestMapping("/getFilesByNoticeid")
	public AbstractResponse getFilesByNoticeid(String noticeid, HttpServletRequest request) {
		try {
			UserDto user = (UserDto) request.getSession().getAttribute("user");
			return fileService.getFilesByNoticeid(Long.parseLong(noticeid), user);
		}catch (Exception e) {
			e.printStackTrace();
			ErrorResponse res = new ErrorResponse();
			res.setStatus("006002");
			res.setErrors(e.getMessage());
			return res;
		}
	}

	/**
	 * 通过notice和user来获取文件信息
	 * @param noticeid 数字
	 * @param request
	 * @return {"notice":"", "user":"", "filename":"", "time":""}
	 */
	@ResponseBody
	@RequestMapping("/getFileByNoticeidAndUserid")
	public AbstractResponse getFileByNoticeidAndUserid(String noticeid, HttpServletRequest request) {
		try {
			UserDto user = (UserDto) request.getSession().getAttribute("user");
			return fileService.getFilesByNoticeidAndUserid(Long.parseLong(noticeid), user);
		}catch (Exception e) {
			e.printStackTrace();
			ErrorResponse res = new ErrorResponse();
			res.setStatus("006002");
			res.setErrors(e.getMessage());
			return res;
		}
	}
	
}
