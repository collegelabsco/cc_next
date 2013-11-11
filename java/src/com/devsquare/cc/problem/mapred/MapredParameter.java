package com.devsquare.cc.problem.mapred;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.devsquare.cc.interfaces.Parameter;

public class MapredParameter implements Parameter {
	
	
	OutputStream os = null;
	Map<String, Object> params;
	MapredOriginalData mod = null;
	public MapredParameter(Map<String, Object> params){
		this.params = params;
	}
	
	public OutputStream getOutputStream(){
		return this.os;
	}
	
	public void setOutputStream(OutputStream outputStream){
		os = outputStream;
	}
	
	public Map<Integer, Integer> getPeopleCountWithAge(){
		return (Map<Integer,Integer>)this.params.get(PEOPLE_AGE);
	}
	
	public void setMapredOrinalData(MapredOriginalData mod){
		this.mod = mod;
	}
	
	public MapredOriginalData getOriginal(){
		if(mod==null){
			mod = new MapredOriginalData(new HashMap<Integer, Integer>());
		}
		return mod;
	}
	

}
