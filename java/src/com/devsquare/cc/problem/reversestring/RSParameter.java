package com.devsquare.cc.problem.reversestring;

import com.devsquare.cc.interfaces.Parameter;

import java.util.Map;

public class RSParameter implements Parameter {


	Map<String, Object> parameter = null;


	public RSParameter(Map<String, Object> params){
		this.parameter = params;
	}

	public String getWord(){
		return null;
	}
	
	
	
	public String[] getResponse(){
		return (String[])parameter.get(RESPONSE);
	}
}
