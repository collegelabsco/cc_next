package com.devsquare.cc.problem.jumbled;

import java.util.Map;

import com.devsquare.cc.interfaces.Parameter;

public class JumbledParameter implements Parameter {

	
	Map<String, Object> parameter = null;
	
	
	public JumbledParameter(Map<String, Object> params){
		this.parameter = params;
	}

	public String getJumbledWord(){
		return (String)parameter.get(JUMBLED_WORD);
	}
	
	
	
	public String[] getResponse(){
		return (String[])parameter.get(RESPONSE);
	}
}
