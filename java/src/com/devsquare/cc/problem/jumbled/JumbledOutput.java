package com.devsquare.cc.problem.jumbled;

import java.util.Map;

import com.devsquare.cc.interfaces.Output;
import com.devsquare.cc.interfaces.Parameter;
import com.devsquare.cc.problem.AbstractOutput;

public class JumbledOutput extends AbstractOutput implements Output<Object> {
	
	Map<String, Object> outputMap = null;
	
	public JumbledOutput(Map<String, Object> outputMap) {
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
