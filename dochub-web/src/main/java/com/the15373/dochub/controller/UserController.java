package com.the15373.dochub.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.the15373.dochub.dto.AbstractResponse;
import com.the15373.dochub.dto.DefaultResponse;
import com.the15373.dochub.dto.ErrorResponse;
import com.the15373.dochub.dto.UserDto;
import com.the15373.dochub.pojo.User;
import com.the15373.dochub.service.UserService;
import com.the15373.dochub.util.GetIP;

/**
 * 用户控制类
 * @author afang
 *
 * @date 2018年6月24日
 */



@Controller
@RequestMapping("/users")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Resource 
	UserService userService;
	
	/**
	 * 修改用户密码
	 * @param oldpassword 原密码
	 * @param newpassword 新密码
	 * @param request
	 * @return 状态码
	 */
	@ResponseBody
	@RequestMapping("/updatePassword")
	public Map<String, String> updatePassword(String oldpassword, String newpassword, HttpServletRequest request){
		try {
			UserDto user = (UserDto)request.getSession().getAttribute("user");
			return userService.updatePassword(new StringBuffer(oldpassword), new StringBuffer(newpassword), user);
		}catch (Exception e) {
			e.printStackTrace();
			Map<String, String> res = new HashMap<String, String>();
			res.put("status",  "001002");
			res.put("errors", e.getMessage());
			return res;
		}
		
	}
	
	/**
	 * 修改用户信息
	 * @param user 用户信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateUserinfo")
	public Map<String, String> updateUserinfo(User user, HttpServletRequest request){
		try {
			UserDto old = (UserDto)request.getSession().getAttribute("user");
			return userService.updateUserinfo(user.toDto(), old);
		}catch (Exception e) {
			e.printStackTrace();
			Map<String, String> res = new HashMap<String, String>();
			res.put("status", "001002");
			res.put("errors", e.getMessage());
			return res;
		}
		
	}
	
	/**
	 * 获取用户关注的用户的信息
	 * @param request
	 * @return [{"account":"", "studentnumber":"", "name":""}, ......]
	 */
	@ResponseBody
	@RequestMapping("/getFriends")
	public AbstractResponse getFriends(HttpServletRequest request) {
		try {
			UserDto user = (UserDto) request.getSession().getAttribute("user");
			return userService.getFriends(user);
		}catch (Exception e) {
			e.printStackTrace();
			ErrorResponse res = new ErrorResponse();
			res.setStatus("001002");
			res.setErrors(e.getMessage());
			return res;
		}
	}
	
	/**
	 * 获取当前用户信息
	 * @param request
	 * @return {"account":"", "studentnumber":"", "name":""}
	 */
	@ResponseBody
	@RequestMapping("/getUserinfo")
	public AbstractResponse getUserinfo(HttpServletRequest request) {
		try {
			UserDto user = (UserDto)request.getSession().getAttribute("user");
			DefaultResponse<UserDto> res = new DefaultResponse<>();
			res.setStatus("1");
			res.setData(user);
			return res;
		}catch (Exception e) {
			e.printStackTrace();
			ErrorResponse res = new ErrorResponse();
			res.setStatus("001002");
			res.setErrors(e.getMessage());
			return res;
		}
		
	}
	
	/**
	 * 用户注册
	 * @param user 用户信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/regist")
	public Map<String, String> regist(User user) {

		try {
			System.out.println("regist() parms : " + user.toString());
			logger.info("regist() parms : " + user.toString());
			UserDto u = user.toDto();
			u.setPassword(user.getPassword());
			return userService.regist(u);
		}catch (Exception e) {
			e.printStackTrace();
			Map<String, String> res = new HashMap<String, String>();
			res.put("status", "001002");
			res.put("errors", e.getMessage());
			return res;
		}
		
	}
	
	/**
	 * 用户登陆
	 * @param account 账号
	 * @param password 密码
	 * @param request
	 * @return status = 1  或 2  表示登陆成功，status = 3 表示账号或密码错误，status = 4 表示操作异常
	 */
	@ResponseBody
	@RequestMapping("/auth")
	public Map<String, String> auth(String account, String password, HttpServletRequest request) {

		System.out.println(account);
		System.out.println(password);
		System.out.println("before");
		try {
			Map<String, String> res = new HashMap<>();

			UserDto user = userService.auth(account, new StringBuffer(password));
			System.out.println("afater");
			if(user != null) {
				@SuppressWarnings("unchecked")
				Map<String, HttpSession> users = (Map<String, HttpSession>)request.getSession().getServletContext().getAttribute("users");
				System.out.println(user.getUserid());
				HttpSession session = users.get(user.getUserid());
				if(session == null) {
					System.out.println(request.getHeader("User-Agent"));
					userService.login(request.getHeader("User-Agent"), GetIP.getIpAddr(request), user);
					request.getSession(true).setAttribute("user", user);
					users.put(user.getUserid() + "", request.getSession());
					res.put("status", 1 + "");
					res.put("name", user.getName());
					return res;
				}
				else {
					
					if(session.getId().equals(request.getSession().getId())) {
						res.put("status", 1 + "");
						res.put("name", user.getName());
						return res;
					}
					else {
						session.invalidate();
						System.out.println(request.getHeader("User-Agent"));
						userService.login(request.getHeader("User-Agent"), GetIP.getIpAddr(request), user);
						request.getSession(true).setAttribute("user", user);
						users.put(user.getUserid() + "", request.getSession());
						res.put("status", 2 + "");
						res.put("name", user.getName());
						return res;
					}
				}
			}
			else {
				res.put("status", 3 + "");
				res.put("errors", "用户名或密码错误");
				return res;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			Map<String, String> res = new HashMap<String, String>();
			res.put("status", 4 + "");
			res.put("errors", e.getMessage());
			return res;
		}
	}
	/**
	 * 用户注销登陆
	 * @param request
	 * @return 请求状态码
	 */
	@ResponseBody
	@RequestMapping("/logout")
	public Map<String, String> logout(HttpServletRequest request){
		Map<String, String> res = new HashMap<>();
		try{
			if(request.getSession() == null) {
				res.put("status", 1 + "");
				return res;
			}
			request.getSession().invalidate();
			res.put("status", 1 + "");
			return res;
			
		} catch (Exception e)
		{
			e.printStackTrace();
			res.put("errors", e.getMessage());
			return res;
		}
	}	
}
