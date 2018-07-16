package com.the15373.dochub.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 获得请求IP地址
 * @author afang
 * @date 20170908
 * @version V1.0
 *
 */

public class GetIP 
{
	public static String getIpAddr(HttpServletRequest request) 
    {
        String ip = request.getHeader("x-forwarded-for"); 
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) 
        {  
            if( ip.indexOf(",")!=-1 )
            {
                ip = ip.split(",")[0];
            }
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) 
        {  
            ip = request.getHeader("Proxy-Client-IP");  
            //System.out.println("Proxy-Client-IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) 
        {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
            //System.out.println("WL-Proxy-Client-IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) 
        {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
            //System.out.println("HTTP_CLIENT_IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) 
        {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
            //System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) 
        {  
            ip = request.getHeader("X-Real-IP");  
            //System.out.println("X-Real-IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) 
        {  
            ip = request.getRemoteAddr();  
            //System.out.println("getRemoteAddr ip: " + ip);
        } 
        return ip;  
    }
}
