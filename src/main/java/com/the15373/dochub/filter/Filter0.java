package com.the15373.dochub.filter;

import java.io.IOException;

import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.the15373.dochub.util.DateUtil;

/**
 * 资源请求过滤器，方便调试，并无其他用处
 * @author afang
 *
 * @date 2018年6月25日
 */
public class Filter0 implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		String uri = ((HttpServletRequest)arg0).getRequestURI();
		
		System.out.printf("%-10s%-35s%-21s\n", "@Filter0 ", uri, " @" + DateUtil.sdf.format(new Date()));
		arg2.doFilter(arg0, arg1);
	}
	

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
