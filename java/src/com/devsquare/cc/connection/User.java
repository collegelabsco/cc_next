package com.devsquare.cc.connection;

import java.util.Map;

import com.devsquare.cc.problem.jumbled.JumbledOutput;

public class User {
	
	public String token;
	public JumbledOutput jop;
	public Map<String, Integer> bitop = null;
	public Map<String, Object> mrMap = null;
	
	public void setToken(String token){
		this.token = token;
	}
	
	public void setJumbledOutput(JumbledOutput jop){
		this.jop = jop;
	}
	
	public String getToken(){
		return this.token;
	}
	
	public JumbledOutput getJumbledOutput(){
		return this.jop;
	}

	public void setBitMapOutput(Map<String, Integer> fileMap) {
		this.bitop = fileMap;
	}
	
	public void setMapredOutput(Map<String, Object> mapredMap) {
		this.mrMap = mapredMap;
	}
	
	public String toString(){
		return "sessiontoken:"+token;
	}

}
