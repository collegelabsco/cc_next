package com.devsquare.cc.connection;

import com.devsquare.cc.problem.bitmap.BitmapOutput;
import com.devsquare.cc.problem.jumbled.JumbledOutput;
import com.devsquare.cc.problem.mapred.MapRedOuput;

public class User {
	
	public String token;
	public JumbledOutput jop;
	public MapRedOuput mrMap = null;
	private BitmapOutput bop = null;
	
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
	
	public void setBitmapOutput(BitmapOutput bop){
		this.bop = bop;
	}
	
	public BitmapOutput getBitmapOutput(){
		return this.bop;
	}

	
	public void setMapredOutput(MapRedOuput mapredMap) {
		this.mrMap = mapredMap;
	}
	
	public MapRedOuput getMapredOutput(){
	  return this.mrMap;
	}
	
	public String toString(){
		return "sessiontoken:"+token;
	}

}
