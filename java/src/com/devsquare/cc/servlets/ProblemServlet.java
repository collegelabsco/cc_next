package com.devsquare.cc.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.devsquare.cc.connection.Event;

public class ProblemServlet extends AbstractServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4317299798799618304L;

	@Override
	public Event getEvent(HttpServletRequest req, HttpServletResponse resp) {
		return new Event(req,resp);
	}

}
