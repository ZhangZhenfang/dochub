package com.the15373.dochub.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传工具类
 * @author afang
 *
 * @date 2018年6月20日
 */

public class UpUtils {

	public static String getSrc(MultipartFile filedata, HttpServletRequest request) {
		String baseDir = request.getSession().getServletContext().getInitParameter("baseDir");
		if(!(baseDir.endsWith("\\") || baseDir.endsWith("/"))) {
			baseDir += baseDir + File.separator;
		}
		if(filedata.getOriginalFilename() == null || filedata.getOriginalFilename() == "") {
			return null;
		}
		
		//获取当前路径，项目部署在tomcat中的根目录
		String realPath = request.getSession().getServletContext().getRealPath("/");
		
		//获取我们的本机工作区间的
		
		//设置文件上传后的缓存路径
		String savePath = baseDir + "tmp/";
		//声明文件路径并且创建该文件目录
		File file = new File(realPath + savePath);
		if(!file.exists()) {
			file.mkdirs();
		}
		String originalFilename = filedata.getOriginalFilename();
		String end = originalFilename.substring(originalFilename.lastIndexOf("."));
		String start = UUID.randomUUID().toString().substring(0, 8);
		originalFilename = start + originalFilename + end;
		try
		{
			//获取文件的名称
			
			//创建文件输入输出流
			String imagePath = savePath + originalFilename;
			FileOutputStream os = new FileOutputStream(imagePath, true);
			os.write(filedata.getBytes());
			os.flush();
			os.close();
			return savePath + originalFilename;
		} catch (Exception e){
			e.printStackTrace();
			return "";
		}
	}
}
