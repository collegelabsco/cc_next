package com.devsquare.cc.problem.jumbled;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.devsquare.cc.CCSystemException;
import com.devsquare.cc.InvalidResult;
import com.devsquare.cc.interfaces.Parameter;
import com.devsquare.cc.interfaces.Problem;
import com.devsquare.cc.util.StringUtil;

public class JumbledWordProblem implements Problem<JumbledOutput,JumbledParameter> {

	
	private static JumbledWordProblem jwp = null;
	
	
	private JumbledWordProblem(){
	}
	
	public static JumbledWordProblem get() throws CCSystemException{
		if(jwp==null){
			jwp = new JumbledWordProblem();
		}
		
		return jwp;
	}
	
	
	public JumbledOutput get(JumbledParameter parameter) throws Exception {
		WordProcessor wp = WordProcessor.getInstance();
		String original = wp.getRandomWord();
		String jw = wp.jumbledTheWord(original);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Parameter.OUTPUT, jw);
		map.put(Parameter.ORIGINAL_WORD, original);
		JumbledOutput jo = new JumbledOutput(map);
		return jo;
		
	}
	
	
	@Override
	public JumbledOutput validate(JumbledParameter parameter){
		
		String jw = parameter.getJumbledWord();
		String sortedJW = StringUtil.sortString(jw);
		String[] response = parameter.getResponse();
		List<String> errorWords = new LinkedList<String>();
		StringBuilder sb = new StringBuilder();
		if(response!=null){
			for(String word:response){
				if(WordProcessor.getInstance().isaValidWord(word)){
					word = StringUtil.sortString(word);
					if(word.equals(sortedJW)) continue;
				}
				errorWords.add(word);
				sb.append(word).append(",");
			}
		}else{
			throw new InvalidResult("result parameter is missing.Please check query string in your url.");
		}
		
		Map<String, Object> outputMap = new HashMap<String, Object>();
		if(errorWords.size()>0){
			String[] errorValues = new String[errorWords.size()];
			errorWords.toArray(errorValues);
			throw new InvalidResult("Invalid words : "+sb.toString());
			//outputMap.put(Parameter.ERROR_OUTPUT, errorValues);
		}
		
		return new JumbledOutput(outputMap);
	}
	
	

}
