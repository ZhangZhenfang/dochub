package com.the15373.dochub.excel.excelservice.impl;

import com.the15373.dochub.dao.ExcelDao;
import com.the15373.dochub.dao.ExcelrecordDao;
import com.the15373.dochub.dto.*;
import com.the15373.dochub.dto.*;
import com.the15373.dochub.pojo.Excel;
import com.the15373.dochub.pojo.User;
import com.the15373.dochub.util.ExcelFileTools;
import com.the15373.dochub.service.ExcelService;
import com.the15373.dochub.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
	@Resource
	private UserService userService;

	@Override
	public AbstractResponse getByUser(UserDto user) {
		UserDto u = userService.getByAccount(user.getAccount());
		User us = new User();
		us.setUserid(Long.parseLong(u.getUserid()));
		List<Excel> list = excelDao.getByUser(us);
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
	public Map<String, String> uploadExcel(String tmpPath, String baseDir, Excel excel, UserDto user) throws Exception{
		Map<String, String> res = new HashMap<>();
		try {
			UserDto u = userService.getByAccount(user.getAccount());
			User us = new User();
			us.setUserid(Long.parseLong(u.getUserid()));
			excel.setUser(us);
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
	public AbstractResponse getExcelFromFriends(UserDto user) {
		UserDto u = userService.getByAccount(user.getAccount());
		List<Excel> list = excelDao.getExcelFromFriends(u);
		List<ExcelDto> data = new ArrayList<>();
		for(Excel e : list) {
			ExcelDto dto = e.toDto();
			dto.setMysubmit(excelrecordDao.getExcelrecordByUseridAndExcelId(e.getExcelid(), Long.parseLong(user.getUserid())));
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
	public Map<String, String> deleteExel(long excelId, UserDto user) {
		Map<String, String> res = new HashMap<String, String>();
		Excel excel = excelDao.getById(excelId);
		if(excel != null) {
			if(excel.getUser().getUserid() == Long.parseLong(user.getUserid())) {
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
	public AbstractResponse getExcelHead(String baseDir, long excelId, UserDto user) {
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
	public String downloadExcel(String baseDir, long excelid, UserDto user) throws Exception{
		Excel excel = excelDao.getByUserIDAndId(Long.parseLong(user.getUserid()), excelid);
		if(excel != null) {
			try {
				String path = baseDir + excel.getPath();
				if(!new File(path).exists()) {
					throw new FileNotFoundException();
				}
				else {
					ArrayList<String[]> data = new ArrayList<>();
					String[] arrString;
					List<ExcelrecordDto> list = excelrecordDao.getExcelreocrdsByExcelId(excelid, Long.parseLong(user.getUserid()));
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
					return tmpPath + "~" + excel.getFilename();
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
	public AbstractResponse excelToTable(String baseDir, long excelId, UserDto user) throws IOException {
		Excel excel = excelDao.getByUserIDAndId(Long.parseLong(user.getUserid()), excelId);
		if(excel != null) {
			String path = baseDir + excel.getPath();
			ArrayList<String[]> data = new ArrayList<>();
			String[] arrString;
			List<ExcelrecordDto> list = excelrecordDao.getExcelreocrdsByExcelId(excelId, Long.parseLong(user.getUserid()));
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
