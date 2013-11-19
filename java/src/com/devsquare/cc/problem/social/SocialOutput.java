package com.devsquare.cc.problem.social;

import java.util.Map;

import com.devsquare.cc.interfaces.Output;

public class SocialOutput implements Output<String> {
	
	private Map<String, Object> socMap;
	private String error;
	public SocialOutput(Map<String, Object> socMap) {
		this.socMap = socMap;
	}

	@Override
	public String getErrorOutput() {
		return error;
	}
	
	public Map<String, Object> getMap(){
		return this.socMap;
	}

}
