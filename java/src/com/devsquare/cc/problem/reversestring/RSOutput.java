package com.devsquare.cc.problem.reversestring;

import com.devsquare.cc.interfaces.Output;
import com.devsquare.cc.interfaces.Parameter;

import java.util.Map;

public class RSOutput implements Output {

	Map<String, Object> outputMap = null;

	public RSOutput(Map<String, Object> outputMap) {
		this.outputMap = outputMap;
	}
	
	public String getResult(){
		return (String)outputMap.get(Parameter.OUTPUT);
	}
	
	public String getOriginal(){
		return (String)outputMap.get(Parameter.ORIGINAL_WORD);
	}
	
	@Override
	public String[] getErrorOutput(){
		return (String[])outputMap.get(Parameter.ERROR_OUTPUT);
	}
	
	

}
