package com.devsquare.cc.connection;

import java.util.Map;

import com.devsquare.cc.problem.bitmap.BitmapOutput;
import com.devsquare.cc.problem.jumbled.JumbledOutput;
import com.devsquare.cc.problem.mapred.MapRedOuput;
import com.devsquare.cc.problem.social.SocialOutput;

public class User {
	
	public String token;
	public JumbledOutput jop;
	public MapRedOuput mrMap = null;
	private BitmapOutput bop = null;
	private SocialOutput socop = null;
	
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

	public void setSocialOutput(SocialOutput sop) {
		this.socop = sop;
		
	}
	
	public SocialOutput getSocialOutput(){
		return this.socop;
	}

}
