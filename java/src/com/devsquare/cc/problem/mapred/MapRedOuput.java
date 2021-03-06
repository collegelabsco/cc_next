package com.devsquare.cc.problem.mapred;

import com.devsquare.cc.interfaces.Output;
import com.devsquare.cc.problem.AbstractOutput;

public class MapRedOuput extends AbstractOutput implements Output<String> {

	
	MapredParameter param = null;
	String error = null;
	
	public MapRedOuput(MapredParameter param) {
		this.param = param;
	}

	
	@Override
	public String getErrorOutput() {
		return error;
	}
	
	public MapredParameter getOutput(){
		return param;
	}
	
	
	

}
