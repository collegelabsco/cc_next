package com.devsquare.cc.connection;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.devsquare.cc.interfaces.Parameter;
import com.devsquare.cc.problem.bitmap.BitmapOutput;
import com.devsquare.cc.problem.bitmap.BitmapProblem;
import com.devsquare.cc.problem.jumbled.JumbledOutput;
import com.devsquare.cc.problem.jumbled.JumbledWordProblem;

public class GetProcess implements Processor {
	
      public String prepareResponse(int level,String token) throws Exception{
    	  
    	  User user = SessionManager.getInstance().getUser(token);
    	  if(user==null){
    		  user = new User();
    		  user.setToken(token);
    		  SessionManager.getInstance().addUser(token,user );
    	  }
    	  
    	  String response = "";
    	  String error = "";
    	  int _level = level;
    	  JSONObject json = new JSONObject();
    	  switch(level){
	    	  case 1:
	    		JumbledWordProblem jwp = JumbledWordProblem.get();
	    		JumbledOutput jo = jwp.get(null);
		    	user.setJumbledOutput(jo);
		    	response = jo.getResult(); 
		    	json.put("dictionaryURL", "http://localhost:8082?"+SessionConstants.FILENAME+"=dictionary.txt");
		    	json.put("output", response);
		    	break;
	    	  case 2:
	    		BitmapProblem bp = BitmapProblem.get();
	    		Map<String, Integer> fileMap = new HashMap<String, Integer>();
	    		for(int i=0;i<2;i++){
	    		  BitmapOutput bo = bp.getRandomFile();
	    		  fileMap.put(bo.get(Parameter.FILE_ID).toString(), (Integer)bo.get(Parameter.FILE_SIZE));
	    		  JSONObject fj = new JSONObject();
	    		  fj.put("file", bo.get(Parameter.FILE_ID));
	    		  fj.put("size", bo.get(Parameter.FILE_SIZE));
	    		  json.accumulate("output", fj.toString());
	    		}
	    		
	    		user.setBitMapOutput(fileMap);
	    		
	    	  case 3:
	    	  case 4:
    	  }
    	  
    	    
	    	json.put("error", error);
	    	json.put("level", _level);
	    	json.put("token", user.getToken());
    	  
    	  return json.toString();
      }

}
