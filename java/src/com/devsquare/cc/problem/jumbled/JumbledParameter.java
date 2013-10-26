package com.devsquare.cc.problem.jumbled;

import com.devsquare.cc.interfaces.Parameter;

public class JumbledParameter implements Parameter {

	int requestType = 0;
	
	public JumbledParameter(int requestType){
		this.requestType = requestType;
	}
	
	public int getRequestType() {
		return requestType;
	}
	

}
