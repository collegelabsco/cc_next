package com.devsquare.cc.problem.jumbled;

import com.devsquare.cc.CCSystemException;
import com.devsquare.cc.interfaces.Problem;

public class JumbledWordProblem implements Problem<JumbledOutput,JumbledParameter> {

	private WordProcessor wp = null;
	
	public JumbledWordProblem() throws CCSystemException{
		this.wp = WordProcessor.getInstance().init();
	}
	
	
	@Override
	public JumbledOutput get(JumbledParameter parameter) throws Exception {
		String original = wp.getRandomWord();
		String jw = wp.jumbledTheWord(original);
		JumbledOutput jo = new JumbledOutput(jw,original);
		return jo;
		
	}
	
	
	
	

}
