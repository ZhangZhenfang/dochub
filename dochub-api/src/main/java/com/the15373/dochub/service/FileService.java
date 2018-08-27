package com.the15373.dochub.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import com.the15373.dochub.dto.UserDto;
import com.the15373.dochub.dto.AbstractResponse;
import org.springframework.http.ResponseEntity;

public interface FileService {

	Map<String, String> uploadFile(String baseDir, String fileName, long noticeid, UserDto user, String md5) throws FileNotFoundException, IOException;

	String downloadFile(String baseDir, long fileid, UserDto user) throws Exception;

	AbstractResponse getFilesByNoticeid(long parseLong, UserDto user);

	AbstractResponse getFilesByNoticeidAndUserid(long parseLong, UserDto user);

}
