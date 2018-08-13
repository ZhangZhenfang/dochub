package com.the15373.dochub.util;

import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * excel内容操作工具类
 * @author afang
 *
 * @date 20170908
 */
public class POI 
{
	public static void main(String[] args)
	{
		System.out.println("Hello!");
	    
	    try
	    {
	    	table table = new table("1");
    	    Workbook wb = new XSSFWorkbook(new FileInputStream("workbook.xlsx"));
    	    Sheet sheet = wb.getSheet(wb.getSheetName(0));
    	    List<CellRangeAddress> mergeCellAddress = sheet.getMergedRegions();
	    	tr tr;
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
    	    			System.out.println("j = " + j + "  k = " + k);
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

		    //Write the output to a file
		    //FileOutputStream fileOut = new FileOutputStream("workbook.xlsx");
		    //wb.write(fileOut);
		    //fileOut.close();   
		    wb.close();
		    
		    System.out.println(table.toString());
	    }catch(Exception e)
	    {
	    	e.printStackTrace();
	    	System.out.println();
	    }
	}	
}

class table
{
	ArrayList<tr> tr = new ArrayList<>();
	private String id;
	public table(String id) {
		this.id = id;
	}
	public void addTr(tr tr)
	{
		this.tr.add(tr);
	}
	public ArrayList<tr> getTable()
	{
		return this.tr;
	}
	
	
	public String getString()
	{
		String result = "";
		for(int i = 0; i < tr.size(); i++)
		{
			result += tr.get(i).toString();
		}
		return "<table id='exceltotable" + id + "' class='table' border='1'>" + result + "</table>";
	}
}
class tr
{
	ArrayList<td> td = new ArrayList<>();
	public void addTd(td td)
	{
		this.td.add(td);
	}
	public ArrayList<td> getTr()
	{
		return this.td;
	}
	public String toString()
	{
		String result = "";
		for(int i = 0; i < td.size(); i++)
		{
			result += td.get(i).getTd();
		}
		return "<tr>" + result + "</tr>";
	}
}
class td
{
	private String td;
	int rowspan = 1;
	int colspan = 1;
	
	public td(String td)
	{
		this.td = td;
	}
	public String getTd()
	{
		return "<td rowspan = '" + rowspan + "' colspan = '" + colspan + "'>" + td + "</td>";
	}
}
