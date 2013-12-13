package com.devsquare.cc.problem;

public abstract class AbstractProblem {
	
	protected long requestTime ;
	
	public void set(long requestTime) {
		// TODO Auto-generated method stub
		this.requestTime = requestTime;
		
	}

	public long getRequestTime() {
		return requestTime;
	}


}
