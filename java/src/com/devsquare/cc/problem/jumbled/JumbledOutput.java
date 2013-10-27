package com.devsquare.cc.problem.jumbled;

import java.util.Map;

import com.devsquare.cc.interfaces.Output;
import com.devsquare.cc.interfaces.Parameter;

public class JumbledOutput implements Output<String,String[]> {
	
	Map<String, Object> outputMap = null;
	
	public JumbledOutput(Map<String, Object> outputMap) {
		this.outputMap = outputMap;
	}
	
	@Override
	public String getOutput(){
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
