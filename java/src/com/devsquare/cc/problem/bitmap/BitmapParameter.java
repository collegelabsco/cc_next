package com.devsquare.cc.problem.bitmap;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.devsquare.cc.interfaces.Parameter;

public class BitmapParameter implements Parameter {

	
	Map<String, Object> params = new HashMap<String, Object>();
	
	public BitmapParameter(Map<String, Object> params) {
		this.params = params;
	}
	
	public int getPosition(){
		return (Integer)params.get(POSITION);
	}
	
	public int getLength(){
		return (Integer)params.get(LENGTH);
	}
	
	public String getFileID(){
		return (String)params.get(FILE_ID);
	}
	
	public List<BitmapRequestLog> getRequestLog(){
		return (List<BitmapRequestLog>)params.get(REQUEST_LOG);
	}
	
	
}
