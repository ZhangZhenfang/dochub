package com.the15373.dochub.service;

import java.util.Map;

import com.the15373.dochub.dto.AbstractResponse;
import com.the15373.dochub.pojo.User;

public interface ExcelrecordService {

	Map<String, String> addExcelrecord(User user, long excelId, String record);

	AbstractResponse getExcelrecordByUseridAndExcelId(long excelId, User user);

	AbstractResponse getExcelreocrdsByExcelId(long parseLong, User user);

}

