package com.the15373.dochub.dao;

import com.the15373.dochub.dto.FileDto;
import com.the15373.dochub.pojo.File;

import java.util.List;

public interface FileDao {

	void save(File file);

	File getById(long fileid, long userid);

	List<FileDto> getByNoticeId(long noticeid);

	File getByNoticeIdAndUserid(long noticeid, Long userid);

	void update(File file);

}
