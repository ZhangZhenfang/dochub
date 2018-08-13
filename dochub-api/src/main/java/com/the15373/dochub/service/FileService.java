package com.the15373.dochub.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import com.the15373.dochub.dto.UserDto;
import org.springframework.http.ResponseEntity;

import com.the15373.dochub.dto.AbstractResponse;
import com.the15373.dochub.pojo.User;

public interface FileService {

	Map<String, String> uploadFile(String baseDir, String tmpPath, String fileName, long noticeid, UserDto user) throws FileNotFoundException, IOException;

	String downloadFile(String baseDir, long fileid, UserDto user) throws Exception;

	AbstractResponse getFilesByNoticeid(long parseLong, UserDto user);

	AbstractResponse getFilesByNoticeidAndUserid(long parseLong, UserDto user);

}
