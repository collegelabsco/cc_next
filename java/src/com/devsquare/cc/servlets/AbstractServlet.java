package com.devsquare.cc.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.devsquare.cc.CCSystemException;
import com.devsquare.cc.connection.Event;
import com.devsquare.cc.problem.bitmap.BitmapProblem;
import com.devsquare.cc.problem.jumbled.WordProcessor;
import com.devsquare.cc.problem.mapred.MapRedProblem;

public abstract class AbstractServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4834579300850088116L;
	
	static Object LOCK = new Object();
	static boolean initCompleted = false;
	
	@Override
	public void init() throws ServletException{
			synchronized(LOCK){
				if(!initCompleted){
					try {
						WordProcessor.getInstance().init();
						BitmapProblem.get().init();
						MapRedProblem.get().init();
					} catch (Exception e) {
						throw new ServletException(e);
					}
				}
			}
			
	}
	

	@Override
	public void service(HttpServletRequest req, HttpServletResponse res){
		Event e = getEvent(req,res);
		e.run();
	}
	
	public abstract Event getEvent(HttpServletRequest req, HttpServletResponse resp);
	
	
	

}
