package com.the15373.dochub.dao;

import java.util.List;

import com.the15373.dochub.dto.ExcelrecordDto;
import com.the15373.dochub.pojo.Excelrecord;

public interface ExcelrecordDao {

	void save(Excelrecord excelrecord);

	Excelrecord getByUserIdAndExcelId(long userid, long excelId);

	void update(Excelrecord excelrecord);

	ExcelrecordDto getExcelrecordByUseridAndExcelId(long excelId, Long userid);

	List<ExcelrecordDto> getExcelreocrdsByExcelId(long excelId, Long userid);

}
