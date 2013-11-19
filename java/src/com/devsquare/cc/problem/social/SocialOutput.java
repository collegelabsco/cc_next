package com.devsquare.cc.problem.social;

import com.devsquare.cc.interfaces.Output;

public class SocialOutput implements Output<String> {
	
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
