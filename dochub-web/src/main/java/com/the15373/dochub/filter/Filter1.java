package com.the15373.dochub.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;


/**
 * 用户权限过滤器，用户没有登录去访问需要登录才能访问的资源时直接返回status = 0
 * @author afang
 *
 * @date 2018年6月25日
 */
@WebFilter("/*")
public class Filter1 implements Filter
{
    public Filter1() {
    }

	public void destroy() {
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		String originHeader = req.getHeader("Origin");
		res.setHeader("Access-Control-Allow-Origin", originHeader);
		res.setHeader("Access-Control-Allow-Methods", "POST, GET");
		res.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		res.setHeader("Access-Control-Allow-Credentials", "true");
		System.out.println("@Filter1 " + req.getRequestURI());
		
		if(req.getRequestURI().endsWith("dochub/") || req.getRequestURI().endsWith("/") || req.getRequestURI().contains("/doc/doc") || req.getRequestURI().endsWith("regist") || req.getRequestURI().endsWith(".html") || req.getRequestURI().endsWith(".jsp") || req.getRequestURI().contains("auth") || req.getRequestURI().startsWith("/withoutAuth") || req.getRequestURI().contains("css/") || req.getRequestURI().contains("js/") || req.getRequestURI().contains("test/") || req.getRequestURI().contains("img/")){
			chain.doFilter(request, response);
		}
		else {
			HttpSession session = req.getSession(false);
			if(session == null){
				res.setHeader("Content-Type", "application/json");
				JSONObject jo = new JSONObject();
				jo.put("status", 0);
				res.getWriter().write(jo.toString());
			}
			else{
				if(session.getAttribute("user") == null){
					res.setHeader("Content-Type", "application/json");
					res.getWriter().write(new JSONObject().put("status", "0").toString());
					return ;
				}
				else {
					chain.doFilter(req, res);
				}
				
			}
		}
	}

	public void init(FilterConfig fConfig) throws ServletException{
	}

}
