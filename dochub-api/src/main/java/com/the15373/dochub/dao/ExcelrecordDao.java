package com.the15373.dochub.dao;

import com.the15373.dochub.dto.ExcelrecordDto;
import com.the15373.dochub.pojo.Excelrecord;

import java.util.List;

public interface ExcelrecordDao {

	void save(Excelrecord excelrecord);

	Excelrecord getByUserIdAndExcelId(long userid, long excelId);

	void update(Excelrecord excelrecord);

	ExcelrecordDto getExcelrecordByUseridAndExcelId(long excelId, Long userid);

	List<ExcelrecordDto> getExcelreocrdsByExcelId(long excelId, Long userid);

}
