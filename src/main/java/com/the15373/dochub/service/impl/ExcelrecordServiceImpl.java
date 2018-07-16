package com.the15373.dochub.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.the15373.dochub.dao.ExcelDao;
import com.the15373.dochub.dao.ExcelrecordDao;
import com.the15373.dochub.dto.AbstractResponse;
import com.the15373.dochub.dto.DefaultResponse;
import com.the15373.dochub.dto.ErrorResponse;
import com.the15373.dochub.dto.ExcelrecordDto;
import com.the15373.dochub.pojo.Excel;
import com.the15373.dochub.pojo.Excelrecord;
import com.the15373.dochub.pojo.User;
import com.the15373.dochub.service.ExcelrecordService;


/**
 * excel表格记录业务类
 * @author afang
 *
 * @date 2018年6月26日
 */

@Service
@Transactional
public class ExcelrecordServiceImpl implements ExcelrecordService{

	@Resource
	private ExcelrecordDao excelrecordDao;
	
	@Resource
	private ExcelDao excelDao;
	
	@Override
	public Map<String, String> addExcelrecord(User user, long excelId, String record) {
		Map<String, String> res = new HashMap<>();
		Excel excel = excelDao.getById(excelId);
		
		Excelrecord er = excelrecordDao.getByUserIdAndExcelId(user.getUserid(), excelId);
		
		//还需进行record字段长度和excel表格需要填写的字段数是否一致，由于时间关系，暂时没有进行验证
		if(excel != null) {
			if(er == null) {
				Excelrecord excelrecord = new Excelrecord();
				excelrecord.setRecord(record);
				excelrecord.setUser(user);
				excelrecord.setExcel(excel);
				excelrecord.setTime(new Date());
				excelrecordDao.save(excelrecord);
			}
			else {
				er.setRecord(record);
				er.setUser(user);
				er.setExcel(excel);
				er.setTime(new Date());
				excelrecordDao.update(er);
			}
			res.put("status", "1");
			return res;
		}
		else {
			res.put("status", "004004");
			res.put("errors", "找不到对应的excel文件记录");
			return res;
		}
	}

	@Override
	public AbstractResponse getExcelrecordByUseridAndExcelId(long excelId, User user) {
		Excel excel = excelDao.getById(excelId);
		if(excel == null) {
			ErrorResponse res = new ErrorResponse();
			res.setStatus("004004");
			res.setErrors("找不到对应的excel文件记录");
			return res;
		}
		else {
			ExcelrecordDto data = excelrecordDao.getExcelrecordByUseridAndExcelId(excelId, user.getUserid());
			DefaultResponse<ExcelrecordDto> res = new DefaultResponse<>();
//			JSONObject jo = JSONObject.fromObject(data);
			res.setStatus("1");
			res.setData(data);
			return res;
		}
	}

	@Override
	public AbstractResponse getExcelreocrdsByExcelId(long excelId, User user) {
		Excel excel = excelDao.getById(excelId);
		if(excel == null) {
			ErrorResponse res = new ErrorResponse();
			res.setStatus("004004");
			res.setErrors("找不到对应的excel文件记录");
			return res;
		}
		if(excel.getUser().getUserid() == user.getUserid()) {
			List<ExcelrecordDto> data = excelrecordDao.getExcelreocrdsByExcelId(excelId, user.getUserid());
			
			DefaultResponse<List<ExcelrecordDto>> res = new DefaultResponse<>();
			res.setStatus("1");
			res.setData(data);
			return res;
		}
		else {
			ErrorResponse res = new ErrorResponse();
			res.setStatus("004005");
			res.setErrors("权限异常");
			return res;
		}
	}
}
