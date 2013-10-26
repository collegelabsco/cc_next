package com.devsquare.cc.interfaces;

public interface Problem<O extends Output,P extends Parameter> {
	
	O get(P parameter) throws Exception;

}
