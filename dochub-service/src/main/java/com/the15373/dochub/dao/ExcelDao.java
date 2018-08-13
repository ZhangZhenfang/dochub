package com.the15373.dochub.dao;

import java.util.List;

import com.the15373.dochub.dto.ExcelDto;
import com.the15373.dochub.pojo.Excel;
import com.the15373.dochub.pojo.User;

public interface ExcelDao {

	void save(Excel excel);

	List<Excel> getByUser(User user);

	List<Excel> getExcelFromFriends(User user);

	List<ExcelDto> getByUserName(String userName);

	List<ExcelDto> getByFileName(String fileName);

	Excel getById(long excelId);

	void delete(Excel excel);

	Excel getByUserIDAndId(Long userid, long excelid);

}
