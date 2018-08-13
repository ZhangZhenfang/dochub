package com.the15373.dochub.util;

import java.io.File;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

/**
 * 压缩文件工具类
 * 
 * @author afang
 * 
 * @date 20170908
 * 
 */

public class Compress
{    
    
	public static void main(String[] args)
	{
//		Compress compress = new Compress("E:\\afang\\workspace\\data\\10000张振方\\测试1\\测试1.zip");
//		compress.compressExe("E:\\afang\\workspace\\data\\10000张振方\\测试1");
	}
	
	
    private File zipFile;    
    
    /** 
     * 压缩文件构造函数， 如果finalFile存在，则删除后压缩。
     * @param pathName 最终压缩生成的压缩文件：目录+压缩文件名.zip 
     */  
    public Compress(String finalFile) 
    {    
        zipFile = new File(finalFile);    
    }    
        
    /** 
     * 执行压缩操作
     * @param srcPathName 需要被压缩的文件/文件夹 
     */  
    public void compressExe(String srcPathName) throws Exception
    {  
    	System.out.println("压缩中...");
    	System.out.println(srcPathName);
    	File srcdir = new File(srcPathName);    
        if(zipFile.exists())
        	zipFile.delete();
    	if (!srcdir.exists())
        {  
            throw new RuntimeException(srcPathName + "不存在！");    
        }   
        if(srcdir.list().length == 0)
        {
        	throw new Exception("dir is null");
        }
        Project prj = new Project();    
        Zip zip = new Zip();    
        zip.setProject(prj);    
        zip.setDestFile(zipFile);    
        FileSet fileSet = new FileSet();    
        fileSet.setProject(prj);    
        
        fileSet.setDir(srcdir);
        //fileSet.setIncludes("**/*.java"); //包括哪些文件或文件夹 eg:zip.setIncludes("*.java");    
        //fileSet.setExcludes(...); //排除哪些文件或文件夹    
        zip.addFileset(fileSet);    
        zip.execute(); 
        System.out.println("压缩完成。");
    }    
}   
