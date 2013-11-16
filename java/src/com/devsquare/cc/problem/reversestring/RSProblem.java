package com.devsquare.cc.problem.reversestring;

import com.devsquare.cc.CCSystemException;
import com.devsquare.cc.interfaces.Parameter;
import com.devsquare.cc.interfaces.Problem;
import com.devsquare.cc.problem.jumbled.JumbledOutput;
import com.devsquare.cc.problem.jumbled.JumbledParameter;
import com.devsquare.cc.problem.jumbled.WordProcessor;
import com.devsquare.cc.util.StringUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RSProblem implements Problem<RSOutput,RSParameter> {

	private static RSProblem jwp = null;


	private RSProblem() throws CCSystemException{
	}
	
	public static RSProblem get() throws CCSystemException{
		if(jwp==null){
			jwp = new RSProblem();
		}
		
		return jwp;
	}
	
	
	public RSOutput get(RSParameter parameter) throws Exception {
        return null;
	}
	
	
	@Override
	public RSOutput validate(RSParameter parameter){
		return null;
	}
	
	

}
