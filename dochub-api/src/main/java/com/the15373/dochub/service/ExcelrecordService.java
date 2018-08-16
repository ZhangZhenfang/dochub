package com.the15373.dochub.service;

import java.util.Map;

import com.the15373.dochub.dto.AbstractResponse;
import com.the15373.dochub.dto.UserDto;

public interface ExcelrecordService {

	Map<String, String> addExcelrecord(UserDto user, long excelId, String record);

	AbstractResponse getExcelrecordByUseridAndExcelId(long excelId, UserDto user);

	AbstractResponse getExcelreocrdsByExcelId(long parseLong, UserDto user);

}

