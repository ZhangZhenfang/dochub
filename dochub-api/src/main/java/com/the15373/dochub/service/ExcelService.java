package com.the15373.dochub.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.the15373.dochub.dto.ExcelDto;
import com.the15373.dochub.dto.UserDto;
import com.the15373.dochub.dto.AbstractResponse;
import com.the15373.dochub.pojo.Excel;
import org.springframework.http.ResponseEntity;

public interface ExcelService {

	Long uploadExcel(String tmpPath, String baseDir, Excel excel, UserDto user) throws Exception;

	boolean update(Excel e);

	AbstractResponse getByUser(UserDto user);

	AbstractResponse getExcelFromFriends(UserDto user);

	AbstractResponse getByUserName(String userName);

	AbstractResponse getByFileName(String fileName);

	Map<String, String> deleteExel(long excelId, UserDto user);

	AbstractResponse getExcelHead(String baseDir, long excelId, UserDto user);

	ArrayList<String[]> downloadExcel(String baseDir, long parseLong, UserDto user) throws Exception;

	ArrayList<String[]> excelToTable(String baseDir, long excelId, UserDto user) throws IOException;

	ExcelDto getById(UserDto user, Long excelid);
}
