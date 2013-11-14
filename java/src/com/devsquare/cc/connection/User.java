package com.devsquare.cc.connection;

import com.devsquare.cc.problem.jumbled.JumbledOutput;

public class User {
	
	public String token;
	public JumbledOutput jop;
	
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

}
