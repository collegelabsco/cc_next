package com.devsquare.cc.problem.jumbled;

public class JumbledWordException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7594404072993720592L;
	
	int ERROR_CODE = -1;
	
	public JumbledWordException(String msg){
		super(msg);
	}
	
	public JumbledWordException(String msg,int errorCode){
		super(msg);
		this.ERROR_CODE = errorCode;
		
	}
}
