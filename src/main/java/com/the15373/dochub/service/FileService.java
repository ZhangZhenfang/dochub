package com.the15373.dochub.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.the15373.dochub.dto.AbstractResponse;
import com.the15373.dochub.pojo.User;

public interface FileService {

	Map<String, String> uploadFile(String baseDir, String tmpPath, String fileName, long noticeid, User user) throws FileNotFoundException, IOException;

	ResponseEntity<byte[]> downloadFile(String baseDir, long fileid, User user) throws Exception;

	AbstractResponse getFilesByNoticeid(long parseLong, User user);

	AbstractResponse getFilesByNoticeidAndUserid(long parseLong, User user);

}
