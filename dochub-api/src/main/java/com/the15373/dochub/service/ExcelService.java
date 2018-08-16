package com.the15373.dochub.service;

import java.io.IOException;
import java.util.Map;

import com.the15373.dochub.dto.UserDto;
import com.the15373.dochub.dto.AbstractResponse;
import com.the15373.dochub.pojo.Excel;
import org.springframework.http.ResponseEntity;

public interface ExcelService {

	Map<String, String> uploadExcel(String tmpPath, String baseDir, Excel excel, UserDto user) throws Exception;

	AbstractResponse getByUser(UserDto user);

	AbstractResponse getExcelFromFriends(UserDto user);

	AbstractResponse getByUserName(String userName);

	AbstractResponse getByFileName(String fileName);

	Map<String, String> deleteExel(long excelId, UserDto user);

	AbstractResponse getExcelHead(String baseDir, long excelId, UserDto user);

	String downloadExcel(String baseDir, long parseLong, UserDto user) throws Exception;

	AbstractResponse excelToTable(String baseDir, long excelId, UserDto user) throws IOException;

}
