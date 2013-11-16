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
		return "RESAETNOIPMAHCEDOC";
	}
	
	public String getOriginal(){
		return "CODECHAMPIONTEASER";
	}
	
	@Override
	public String[] getErrorOutput(){
        String[] strarr ={"ERROR"};
		return strarr;//(String[])outputMap.get(Parameter.ERROR_OUTPUT);
	}
	
	

}
