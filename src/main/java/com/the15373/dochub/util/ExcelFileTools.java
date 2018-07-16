package com.the15373.dochub.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * excel表格相关操作工具类
 * @author afang
 *
 * @date 20170908
 */
public class ExcelFileTools 
{
	public static void main(String[] args)
	{
//		System.out.println(Excel2Table("workbook.xlsx"));
	}
	
	public static void addExcel(File file, File out, ArrayList<String[]> args, int l) throws IOException 
	{  
		Workbook wb = null;
    	if(file.getPath().endsWith(".xlsx"))
    		wb = new XSSFWorkbook(new FileInputStream(file));
    	else if(file.getPath().endsWith(".xls"))
    		wb = new HSSFWorkbook(new FileInputStream(file));
    	else
    		return ;
	    Sheet sheet = wb.getSheet(wb.getSheetName(0)); 
	    int length = sheet.getLastRowNum() + 1;  
	    for (int i = 0; i < args.size(); i++) 
	    {  
	    	Row row = sheet.createRow(length++);
	    	for(int j = 0; j < args.get(i).length; j++)
	    	{
	    		Cell c = row.createCell(j);
	    		c.setCellValue(args.get(i)[j]);
	    	}
	    }  
	    FileOutputStream o = new FileOutputStream(out); 
	    wb.write(o);  
	    wb.close();
	    o.close();
	} 
	
	
	public static String Excel2Table(String path, String id)
	{
		String result = "";
	    try
	    {
	    	Workbook wb = null;
	    	table table = new table(id);
	    	if(path.endsWith(".xlsx"))
	    		wb = new XSSFWorkbook(new FileInputStream(path));
	    	else if(path.endsWith(".xls"))
	    		wb = new HSSFWorkbook(new FileInputStream(path));
	    	else
	    		return "";
    	    Sheet sheet = wb.getSheet(wb.getSheetName(0));
    	    List<CellRangeAddress> mergeCellAddress = sheet.getMergedRegions();
	    	tr tr;
	    	int rownumber = sheet.getLastRowNum();
	    	int colnumber = sheet.getRow(0).getLastCellNum();
	    	
    	    for(int i = 0; i <= sheet.getLastRowNum(); i++)
    	    {
    	    	td td = null;
    	    	tr = new tr();
    	    	Row row = sheet.getRow(i);
    	    	if(row == null)
    	    	{
    	    		table.addTr(null);
    	    		continue;
    	    	}
    	    	for(int j = 0; j < row.getLastCellNum(); j++)
    	    	{
    	    		if(sheet.getRow(i).getCell(j) == null)
    	    		{
    	    			td = new td("");
    	    			tr.addTd(td);
    	    			continue;
    	    		}
	    			td = new td(sheet.getRow(i).getCell(j).toString());
	    			tr.addTd(td);
    	    	}
        	   	table.addTr(tr);
    	    }
    	   	
    	    int fc, lc, fr, lr;
    	    for(int i = 0; i < mergeCellAddress.size(); i++)
    	    {
    	    	fc = mergeCellAddress.get(i).getFirstColumn();
    	    	lc = mergeCellAddress.get(i).getLastColumn();
    	    	fr = mergeCellAddress.get(i).getFirstRow();
    	    	lr = mergeCellAddress.get(i).getLastRow();
    	    	for(int j = fr; j <= lr; j++)
    	    	{
    	    		for(int k = fc; k <= lc; k++)
        	    	{
    	    			if(j == fr && k == fc)
    	    			{
    	    				table.tr.get(j).td.get(k).colspan = 1 + lc - fc; 
    	    				table.tr.get(j).td.get(k).rowspan = 1 + lr - fr; 
    	    			}
    	    			else
    	    			{
    	    				table.tr.get(j).td.get(k).colspan = 0;
    	    			}
        	    	}
    	    	}
    	    	
    	    }
    	    for(int i = 0; i < table.tr.size(); i++)
    	    {
    	    	if(table.tr.get(i) == null)
    	    	{
    	    		table.tr.remove(i--);
    	    		continue;
    	    	}
    	    	for(int j = 0; j < table.tr.get(i).td.size(); j++)
    	    	{
    	    		if(table.tr.get(i).td.get(j).colspan == 0)
    	    		{
    	    			table.tr.get(i).td.remove(j--);
    	    		}
    	    	}
    	    }
		    wb.close();
		    result = table.getString() + "::" + rownumber + "::" + colnumber;
	    }catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
		return result;
	}	
}
