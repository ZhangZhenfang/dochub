package com.the15373.dochub.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

import com.the15373.dochub.dao.ExcelDao;
import com.the15373.dochub.dao.ExcelrecordDao;
import com.the15373.dochub.dto.AbstractResponse;
import com.the15373.dochub.dto.DefaultResponse;
import com.the15373.dochub.dto.ErrorResponse;
import com.the15373.dochub.dto.ExcelDto;
import com.the15373.dochub.dto.ExcelrecordDto;
import com.the15373.dochub.pojo.Excel;
import com.the15373.dochub.pojo.User;
import com.the15373.dochub.service.ExcelService;
import com.the15373.dochub.util.ExcelFileTools;

import net.sf.json.JSONObject;


/**
 * excel表格业务类
 * @author afang
 *
 * @date 2018年6月25日
 */



@Service
@Transactional
public class ExcelServiceImpl implements ExcelService{

	@Resource
	private ExcelDao excelDao;
	@Resource
	private ExcelrecordDao excelrecordDao;
	
	@Override
	public AbstractResponse getByUser(User user) {
		List<Excel> list = excelDao.getByUser(user);
		DefaultResponse<List<ExcelDto>> res = new DefaultResponse<>();
		List<ExcelDto> data = new ArrayList<>();
		for(Excel e : list) {
			data.add(e.toDto());
		}
		res.setData(data);
		res.setStatus("1");
		return res; 
	}

	@Override
	public Map<String, String> uploadExcel(String tmpPath, String baseDir, Excel excel) throws Exception{
		Map<String, String> res = new HashMap<>();
		try {
			excel.setPath("excelfiles/" + excel.getUser().getUserid() + "/" + excel.getFilename());
			File file = new File(baseDir + "excelfiles/" + excel.getUser().getUserid());
			if(!file.exists()) {
				file.mkdirs();
			}
			FileInputStream is = new FileInputStream(new File(tmpPath));
			FileOutputStream os = new FileOutputStream(new File(baseDir + excel.getPath()));
			int len;
			byte[] bytes = new byte[2048];
			
			while((len = is.read(bytes)) != -1) {
				os.write(bytes, 0, len);
			}
			is.close();
			os.close();
			if(new File(baseDir + excel.getPath()).exists()) {
				excelDao.save(excel);
				res.put("status", 1 + "");
				return res;
			}
			else {
				res.put("status", 2 + "");
				return res;
			}
		} catch (FileNotFoundException e){
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public AbstractResponse getExcelFromFriends(User user) {
		List<Excel> list = excelDao.getExcelFromFriends(user);
		List<ExcelDto> data = new ArrayList<>();
		for(Excel e : list) {
			ExcelDto dto = e.toDto();
			dto.setMysubmit(excelrecordDao.getExcelrecordByUseridAndExcelId(e.getExcelid(), user.getUserid()));
//			System.out.println(e.getDescription());
			data.add(dto);
		}
		
		
		DefaultResponse<List<ExcelDto>> res = new DefaultResponse<>();
		res.setData(data);
		res.setStatus("1");
		return res; 
		
	}

	@Override
	public AbstractResponse getByUserName(String userName) {
		List<ExcelDto> data = excelDao.getByUserName(userName);
		DefaultResponse<List<ExcelDto>> res = new DefaultResponse<>();
		res.setData(data);
		res.setStatus("1");
		return res; 
	}

	@Override
	public AbstractResponse getByFileName(String fileName) {
		List<ExcelDto> data = excelDao.getByFileName(fileName);
		DefaultResponse<List<ExcelDto>> res = new DefaultResponse<>();
		res.setData(data);
		res.setStatus("1");
		return res; 
	}

	@Override
	public Map<String, String> deleteExel(long excelId, User user) {
		Map<String, String> res = new HashMap<String, String>();
		Excel excel = excelDao.getById(excelId);
		if(excel != null) {
			if(excel.getUser().getUserid() == user.getUserid()) {
				excelDao.delete(excel);
				res.put("status", 1 + "");
				return res;
			}
			else {
				res.put("status", "003005");
				res.put("errors", "权限异常");
				return res;
			}
		}
		else {
			res.put("status", "003004");
			res.put("errors", "找不到对应的excel文件记录");
			return res;
		}
	}

	@Override
	public AbstractResponse getExcelHead(String baseDir, long excelId, User user) {
		Excel excel = excelDao.getById(excelId);
		if(excel != null) {
			DefaultResponse<String> res = new DefaultResponse<>();
			String data = ExcelFileTools.Excel2Table(baseDir + excel.getPath(), excelId + "");
			System.out.println(data);
			System.out.println(data);
			res.setStatus("1");
			res.setData(data);
			return res;
		}
		else {
			ErrorResponse res = new ErrorResponse();
			res.setStatus("003004");
			res.setErrors("找不到对应的excel文件记录");
			return res;
		}
	}

	@Override
	public ResponseEntity<byte[]> downloadExcel(String baseDir, long excelid, User user) throws Exception{
		Excel excel = excelDao.getByUserIDAndId(user.getUserid(), excelid);
		if(excel != null) {
			try {
				String path = baseDir + excel.getPath();
				if(!new File(path).exists()) {
					throw new FileNotFoundException();
				}
				else {
					ArrayList<String[]> data = new ArrayList<>();
					String[] arrString;
					List<ExcelrecordDto> list = excelrecordDao.getExcelreocrdsByExcelId(excelid, user.getUserid());
					for(ExcelrecordDto e : list) {
						JSONObject jo = JSONObject.fromObject(e.getRecord());
						arrString = new String[jo.size()];
						for(int i = 0; i < jo.size(); i++) {
							arrString[i] = jo.getString(i + "");
						}
						data.add(arrString);
					}
					String tmpPath = path.substring(0, path.lastIndexOf("/")) + excel.getExcelid() + "tmp" + excel.getFilename();
					ExcelFileTools.addExcel(new File(path), new File(tmpPath), data, data.size());
					
					File file = new File(tmpPath);
					HttpHeaders headers = new HttpHeaders();  
					//下载显示的文件名，解决中文名称乱码问题  
					String downloadFielName = new String(excel.getFilename().getBytes("UTF-8"),"iso-8859-1");
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
	public AbstractResponse excelToTable(String baseDir, long excelId, User user) throws IOException {
		Excel excel = excelDao.getByUserIDAndId(user.getUserid(), excelId);
		if(excel != null) {
			String path = baseDir + excel.getPath();
			ArrayList<String[]> data = new ArrayList<>();
			String[] arrString;
			List<ExcelrecordDto> list = excelrecordDao.getExcelreocrdsByExcelId(excelId, user.getUserid());
			for(ExcelrecordDto e : list) {
				JSONObject jo = JSONObject.fromObject(e.getRecord());
				arrString = new String[jo.size()];
				for(int i = 0; i < jo.size(); i++) {
					arrString[i] = jo.getString(i + "");
				}
				data.add(arrString);
			}
			String tmpPath = path.substring(0, path.lastIndexOf("/")) + excel.getExcelid() + "tmp" + excel.getFilename();
			ExcelFileTools.addExcel(new File(path), new File(tmpPath), data, data.size());
			String str = ExcelFileTools.Excel2Table(tmpPath, excelId + "");
			DefaultResponse<String> res = new DefaultResponse<>();
			res.setData(str);
			res.setStatus("1");
			return res;
		}
		else {
			ErrorResponse res = new ErrorResponse();
			res.setStatus("003004");
			res.setErrors("表格不存在");
			return res;
		}
		
	}
	
}
