package com.devsquare.cc;

public class CCSystemException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5029994301416648123L;
	
	int errorCode = -1;
	
	public CCSystemException(String msg, int errorCode){
		super(msg);
		this.errorCode = errorCode;
	}
	
	public CCSystemException(Throwable e, int errorCode){
		super(e);
		this.errorCode = errorCode;
	}

}
