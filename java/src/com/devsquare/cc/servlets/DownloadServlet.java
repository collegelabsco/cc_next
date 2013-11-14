package com.devsquare.cc.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.devsquare.cc.connection.Event;
import com.devsquare.cc.connection.FileEvent;

public class DownloadServlet extends AbstractServlet {

	private static final long serialVersionUID = 9115010656591810037L;
	
	public Event getEvent(HttpServletRequest req, HttpServletResponse res){
		
		return new FileEvent(req,res );
		
	}
}
