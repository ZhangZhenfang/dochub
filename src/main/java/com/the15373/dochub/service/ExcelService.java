package com.the15373.dochub.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.the15373.dochub.dto.AbstractResponse;
import com.the15373.dochub.pojo.Excel;
import com.the15373.dochub.pojo.User;

public interface ExcelService {

	Map<String, String> uploadExcel(String tmpPath, String baseDir, Excel excel) throws Exception;

	AbstractResponse getByUser(User user);

	AbstractResponse getExcelFromFriends(User user);

	AbstractResponse getByUserName(String userName);

	AbstractResponse getByFileName(String fileName);

	Map<String, String> deleteExel(long excelId, User user);

	AbstractResponse getExcelHead(String baseDir, long excelId, User user);

	ResponseEntity<byte[]> downloadExcel(String baseDir, long parseLong, User user) throws Exception;

	AbstractResponse excelToTable(String baseDir, long excelId, User user) throws IOException;

}
