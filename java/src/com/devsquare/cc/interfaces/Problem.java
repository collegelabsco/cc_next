package com.devsquare.cc.interfaces;



public interface Problem<O extends Output<?>,P extends Parameter> {
	
	O validate(P parameter);
	
	void set(long requestTime);
	
	long getRequestTime();


}
