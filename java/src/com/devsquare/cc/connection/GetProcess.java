package com.devsquare.cc.connection;

import org.json.JSONObject;

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
    	  
    	  switch(level){
	    	  case 1:
	    		JumbledWordProblem jwp = JumbledWordProblem.get();
	    		JumbledOutput jo = jwp.get(null);
		    	user.setJumbledOutput(jo);
		    	response = jo.getResult(); 
		    	JSONObject json = new JSONObject();
		    	json.put("output", response);
		    	json.put("error", "");
		    	json.put("level", 1);
		    	json.put("token", user.getToken());
		    	json.put("dictionaryURL", "http://localhost:8082?"+SessionConstants.FILENAME+"=dictionary.txt");
		    	response = json.toString();
		    	break;
	    	  case 2:
	    	  case 3:
	    	  case 4:
    	  }
    	  
    	  return response;
      }

}
