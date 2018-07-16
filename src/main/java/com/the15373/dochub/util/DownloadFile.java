package com.the15373.dochub.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 文件下载工具类
 * @author afang
 * @date 20170908
 * @version V1.0
 */


public class DownloadFile 
{
	/**
	 * 下载文件工具类
	 * @param request
	 * @param response
	 * @param inFilePath 源文件路径
	 * @param outFileName 输出文件名
	 * 
	 */
	public static void download(HttpServletRequest request, HttpServletResponse response, String inFilePath, String outFileName, JSONArray errors)
	throws Exception
	{
		String userAgent = request.getHeader("User-Agent");
		try
		{
			if(userAgent.contains("IE") || userAgent.contains("Edge"))//IE或者Edge浏览器
			{
				System.out.print("IE || Edge : ");
				System.out.print(outFileName + " ");
				outFileName = URLEncoder.encode(outFileName, "UTF-8");
				System.out.println(outFileName);
				response.setHeader("Content-Disposition", "attachment;filename=" + outFileName); 
			}
			else//其它浏览器
			{
				System.out.print("Other : ");
				System.out.print(outFileName + " ");
				outFileName = new String(outFileName.getBytes("UTF-8"), "ISO-8859-1");
				System.out.println(outFileName);
				response.setHeader("Content-Disposition", "attachment;filename=" + outFileName.replaceAll(" ", "").trim());  
			}
			File in = new File(inFilePath);
			System.out.println(inFilePath);
			response.setContentType(request.getServletContext().getMimeType(inFilePath));
			System.out.println(0);
			try
			{
				InputStream is = new FileInputStream(in);  
				OutputStream out = response.getOutputStream();  
				byte buffer[] = new byte[2048];  
				int l = 0;  
				System.out.println(1);
				while((l=is.read(buffer))!= -1)  
				{  
					out.write(buffer,0, l);  
				}  
				System.out.println(2);
				out.flush();
				is.close();
			}catch(Exception e)
			{
				e.printStackTrace();
				errors.add(new JSONObject().put("whenfindfile", e.getMessage()));
				throw e;
			}
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			errors.add(new JSONObject().put("whenfindfile", e.getMessage()));
			throw e;
		}
	}
}
