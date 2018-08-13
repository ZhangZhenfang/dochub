package com.the15373.dochub.listener;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;

/**
 * Application Lifecycle Listener implementation class ContextLinster
 *
 */
@WebListener
public class ContextLinster implements ServletContextListener {

    public ContextLinster() {
    }

    public void contextDestroyed(ServletContextEvent arg0)  { 
    	@SuppressWarnings("unchecked")
		Map<String, HttpSession> users = (Map<String, HttpSession>)arg0.getServletContext().getAttribute("users");
    	for(String key : users.keySet()) {
    		users.get(key).invalidate();
    	}
    }

    public void contextInitialized(ServletContextEvent arg0)  {
    	System.out.println("\n***************\ncontextInitialized\n*************\n");
    	Map<String, HttpSession> users = new Hashtable<>();
    	users = Collections.synchronizedMap(users);
    	arg0.getServletContext().setAttribute("users", users);
    }
	
}
