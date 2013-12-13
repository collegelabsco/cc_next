package com.devsquare.cc.problem.reversestring;

import com.devsquare.cc.CCSystemException;
import com.devsquare.cc.interfaces.Problem;
import com.devsquare.cc.problem.AbstractProblem;

public class RSProblem extends AbstractProblem implements Problem<RSOutput,RSParameter> {

	private static RSProblem jwp = null;

    RSOutput rsOutput = new RSOutput(null);

	private RSProblem() throws CCSystemException{
	}
	
	public static RSProblem get() throws CCSystemException{
		if(jwp==null){
			jwp = new RSProblem();
		}
		
		return jwp;
	}
	
	
	public RSOutput get(RSParameter parameter) throws Exception {
        return rsOutput;
	}
	
	
	@Override
	public RSOutput validate(RSParameter parameter){
		return rsOutput;
	}
	
	

}
