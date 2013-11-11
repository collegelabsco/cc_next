package com.devsquare.cc.problem.bitmap;

import java.util.Map;

import com.devsquare.cc.interfaces.Output;

public class BitmapOutput implements Output<String>{
	
    Map<String, ? extends Object> params = null;
	
	public BitmapOutput(Map<String, ? extends Object> map){
		this.params = map;
	}

	
	public Object get(String key){
		return params.get(key);
	}
	
	@Override
	public String getErrorOutput() {
		return null;
	}

}
