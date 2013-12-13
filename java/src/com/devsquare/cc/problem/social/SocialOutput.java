package com.devsquare.cc.problem.social;

import com.devsquare.cc.interfaces.Output;
import com.devsquare.cc.problem.AbstractOutput;

public class SocialOutput extends AbstractOutput implements Output<String> {
	
	SocialParameter socParam = null;
	private String error;
	public SocialOutput(SocialParameter socialParameter) {
		this.socParam = socialParameter;
	}

	@Override
	public String getErrorOutput() {
		return error;
	}
	
	public SocialParameter getParameter(){
		return socParam;
	}
}
