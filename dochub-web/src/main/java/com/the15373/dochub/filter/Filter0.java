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
import javax.servlet.http.HttpSession;

import com.the15373.dochub.dto.UserDto;
import com.the15373.dochub.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 资源请求过滤器，方便调试，并无其他用处
 * @author afang
 *
 * @date 2018年6月25日
 */
public class Filter0 implements Filter{

	private Logger logger = LoggerFactory.getLogger(Filter0.class);
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		String uri = ((HttpServletRequest)arg0).getRequestURI();
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpSession session = request.getSession(false);
		String u = null;
		if(session != null){
			UserDto user = (UserDto) session.getAttribute("user");
			if(user != null){
				u = user.getUserid();
			}
		}
		logger.info("@Filter0 " + u + " request " + uri + "@" + DateUtil.sdf.format(new Date()));
//		System.out.printf("%-10s%-35s%-21s\n", "@Filter0 ", uri, " @" + DateUtil.sdf.format(new Date()));
		arg2.doFilter(arg0, arg1);
	}
	

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
