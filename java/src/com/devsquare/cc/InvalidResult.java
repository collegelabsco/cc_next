package com.devsquare.cc;

public class InvalidResult extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3195732218485011530L;

	public InvalidResult(String errorMsg){
		super(errorMsg);
	}
	
}
