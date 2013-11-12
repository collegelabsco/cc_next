package com.devsquare.cc.problem.mapred;

import java.util.HashMap;
import java.util.Map;

import com.devsquare.cc.interfaces.Constants;
import com.devsquare.cc.interfaces.Output;
import com.devsquare.cc.interfaces.Parameter;

public class MapRedOuput implements Output<String> {

	Map<String, Object> output = new HashMap<String, Object>();
	
	public MapRedOuput(Map<String, Object> outputMap) {
		this.output = outputMap;
	}

	@Override
	public String getErrorOutput() {
		return (String)output.get(Parameter.ERROR_OUTPUT);
	}
	
	
	

}
