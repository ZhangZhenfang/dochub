package com.the15373.dochub.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.the15373.dochub.dto.AbstractResponse;
import com.the15373.dochub.dto.DefaultResponse;
import com.the15373.dochub.dto.ErrorResponse;
import com.the15373.dochub.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.the15373.dochub.pojo.Relationship;
import com.the15373.dochub.service.RelationshipService;
import com.the15373.dochub.service.UserService;



@Controller
@RequestMapping("/relations")
public class RelationshipController {
	
	@Resource
	RelationshipService relationshipService;
	@Resource 
	UserService userService;
	
	
	/**
	 * 获取关注信息
	 * @return status = 1  表示获取成功，status = 004001 表示操作异常
	 */
	@ResponseBody
	@RequestMapping("/getRelationship")
    public AbstractResponse getRelationship(HttpServletRequest request) {
		try {
			
			UserDto user=(UserDto)request.getSession().getAttribute("user");
			List<UserDto> list= relationshipService.getByUserid(Long.parseLong(user.getUserid()));
			DefaultResponse<List<UserDto>> res = new DefaultResponse<>();
			res.setStatus("1");
			res.setData(list);
			return res;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ErrorResponse res = new ErrorResponse();
			res.setStatus("004001");
			res.setErrors("操作异常");
			return res;
		}
		
		
			
	}
	/**
	 * 添加关注
	 * @param account
	 * @return status = 1  或 表示关注成功，status = 2 被关注人不存在，status = 3表示操作异常
	 */
	@ResponseBody
	@RequestMapping("/addRelationship")
	public Map<String, String> addRelationship(String account,HttpServletRequest request) {
		
	try {
		
		UserDto user=(UserDto) request.getSession().getAttribute("user");
	    UserDto friend=userService.getByAccount(account);
	    if(friend!=null)
	    {
	        return relationshipService.addRelationship(account, user);
	    }
	    else
	    {
	    	Map<String, String> map=new HashMap<String,String>();
			map.put("status", 2 + "");
			map.put("errors", "被关注人不存在");
			return map;
	    }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Map<String, String> map=new HashMap<String,String>();
			map.put("status", 3 + "");
			map.put("errors", e.getMessage());
			return map;
		}
	}
		
	/**
	 * 删除关注
	 * @param account
	 * @return status = 1  或 表示删除关注成功，status = 3表示操作异常
	 */
	@ResponseBody
	@RequestMapping("/deleRelationship")
	public Map<String, String> deleRelationship(String account, HttpServletRequest request) {
		
		try {
			UserDto user=(UserDto) request.getSession().getAttribute("user");
			UserDto friend=userService.getByAccount(account);
			Relationship relation= relationshipService.getRelationship(user,Long.parseLong(friend.getUserid()));
		    return 	relationshipService.deleRelationship(relation);	
		}
	    catch (Exception e) {
			// TODO: handle exception
	    	e.printStackTrace();
	    	Map<String, String> map=new HashMap<String,String>();
			map.put("status", 3 + "");
			map.put("errors", e.getMessage());
			return map;
		}
	}
}
