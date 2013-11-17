package com.devsquare.cc.connection;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;

import com.devsquare.cc.interfaces.Parameter;
import com.devsquare.cc.log.Log;
import com.devsquare.cc.problem.bitmap.BitmapOutput;
import com.devsquare.cc.problem.bitmap.BitmapProblem;
import com.devsquare.cc.problem.jumbled.JumbledOutput;
import com.devsquare.cc.problem.jumbled.JumbledWordProblem;
import com.devsquare.cc.problem.jumbled.WordProcessor;
import com.devsquare.cc.problem.mapred.MapRedOuput;
import com.devsquare.cc.problem.mapred.MapredParameter;

public class GetProcess implements Processor {
	
      public String prepareResponse(int level,String token) throws Exception{
    	  
    	  User user = SessionManager.getInstance().getUser(token);
    	  if(user==null){
    		  user = new User();
    		  user.setToken(token);
    		  Log.info("Saving user detail "+user.toString());
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
		    	JSONObject job = new JSONObject();
		    	job.put(Parameter.FILE_ID, SessionConstants.DICTIONARY_FILE_NAME);
		    	job.put(Parameter.FILE_SIZE, WordProcessor.getInstance().getDictionaryFileSize());
		    	json.put("dictionary",job.toString());
		    	
		    	json.put("output", response);
		    	break;
	    	  case 2:
	    		BitmapProblem bp = BitmapProblem.get();
	    		  BitmapOutput bo = bp.getRandomFile();
	    		  JSONObject fj = new JSONObject();
	    		  fj.put(Parameter.FILE_ID, bo.get(Parameter.FILE_ID));
	    		  fj.put(Parameter.FILE_SIZE, bo.get(Parameter.FILE_SIZE));
	    		  json.put("output", fj.toString());
	    		  
	    		  for(int i=0;i<SessionConstants.BITMAP_FILE_COUNT;i++){
	    			  String fuid = UUID.randomUUID().toString();
	    			  String context = "/d?"+Parameter.FILE_ID+"="+fuid;
	    			  json.accumulate("context", context);
	    			  bo.add(fuid, null);
	    		  }
	    		
	    		 user.setBitmapOutput(bo);
	    		
	    	  case 3:
	    		  break;
	    	  case 4:
	    		  int filelistLimit = 1;// SessionConstants.MAPRED_FILECOUNT;
	    		  Map<String, Object> mapredfileMap = new HashMap<String, Object>();
	    		  String filePrefix = "mapred_";
	    		  String fName="";
		    		for(int i=0;i<filelistLimit;i++){
		    		  fName = filePrefix+System.currentTimeMillis()+".txt";
		    		  mapredfileMap.put(fName, null);
		    		  JSONObject jf = new JSONObject();
		    		  jf.put(Parameter.FILE_ID,fName);
		    		  json.accumulate("output", jf.toString());
		    		}
		    		
		    		MapRedOuput mro = new MapRedOuput(new MapredParameter(mapredfileMap));
		    		user.setMapredOutput(mro);
	    		  break;
    	  }
    	  
    	    
	    	json.put("error", error);
	    	json.put("level", _level);
	    	json.put("token", user.getToken());
    	  
    	  return json.toString();
      }

}
