package com.devsquare.cc.problem.social;

import java.io.OutputStream;
import java.util.Map;

import com.devsquare.cc.interfaces.Parameter;

public class SocialParameter implements Parameter{
	
	private Map<String, Object> socMap; 
	private OutputStream os = null;
	
	public SocialParameter(Map<String, Object> socMap){
		this.socMap = socMap;
	}
	
	public String getPersonName(){
		return (String)this.socMap.get(Parameter.PERSON_NAME);
	}
	
	public String getFileName(){
		return (String)this.socMap.get(Parameter.FILE_ID);
	}

	public OutputStream getOutputStream() {
		return os;
	}
	
	public void setOutputStream(OutputStream os){
		this.os = os;
	}
	
	

}
