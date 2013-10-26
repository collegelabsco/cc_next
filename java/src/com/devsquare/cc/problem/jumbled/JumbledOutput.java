package com.devsquare.cc.problem.jumbled;

import com.devsquare.cc.interfaces.Output;

public class JumbledOutput implements Output<String> {
	
	private String jw = null;
	private String original = null;

	public JumbledOutput(String jw,String original) {
		this.jw = jw;
		this.original = original;
	}
	
	@Override
	public String getOutput(){
		return jw;
	}
	
	public String getOriginal(){
		return this.original;
	}

}
