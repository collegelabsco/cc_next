package com.devsquare.cc.problem.bitmap;

import java.util.Map;

import com.devsquare.cc.interfaces.Output;

public class BitmapOutput implements Output<String>{
	
    Map<String, Object> params = null;
    String fileName = null;
	
	public BitmapOutput(Map<String, Object> map){
		this.params = map;
	}

	
	public Object get(String key){
		return params.get(key);
	}
	
	@Override
	public String getErrorOutput() {
		return null;
	}
	
	public void add(String key, Object param){
		this.params.put(key, param);
	}
	
	public boolean has(String key){
		return params.containsKey(key);
	}

	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	
	public String getFileName(){
		return this.fileName;
	}
}
