package com.the15373.dochub.listener;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.the15373.dochub.pojo.User;

/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 */
@WebListener
public class SessionListener implements HttpSessionListener{

    public SessionListener() {
    }

    public void sessionCreated(HttpSessionEvent arg0)  { 
    	System.out.println("/n****************/nsession created " + arg0.getSession().getId() + "/n***************************/n");
    }

    public void sessionDestroyed(HttpSessionEvent arg0)  {
    	System.out.println("/n****************/nsession destroyed " + arg0.getSession().getId() + "/n***************************/n");
    	
    	@SuppressWarnings("unchecked")
		Map<String, HttpSession> users = (Map<String, HttpSession>)arg0.getSession().getServletContext().getAttribute("users");
    	User user = (User)arg0.getSession().getAttribute("user");
    	System.out.println("beforelogout " + user.getUserid());
    	for(String key : users.keySet()) {
			System.out.println(((User)users.get(key).getAttribute("user")).getUserid());
		}
    	Iterator<Map.Entry<String, HttpSession>> it = users.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, HttpSession> entry = it.next();
			String key = entry.getKey();
			if(key.equals(user.getUserid() + "")){
				it.remove();   
				users.remove(key);
			}
		}
		System.out.println("afterlogout " + user.getUserid());
		for(String key : users.keySet()) {
			System.out.println(((User)users.get(key).getAttribute("user")).getUserid());
		}
    }
}
