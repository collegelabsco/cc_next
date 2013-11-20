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
import com.devsquare.cc.problem.social.SocialOutput;
import com.devsquare.cc.problem.social.SocialParameter;

public class GetProcess implements Processor {
	
      public String prepareResponse(int level,String token) throws Exception{
    	  
    	  User user = SessionManager.getInstance().getUser(token);
//    	  if(user==null){
//    		  user = new User();
//    		  user.setToken(token);
//    		  Log.info("Saving user detail "+user.toString());
//    		  SessionManager.getInstance().addUser(token,user );
//    	  }
//    	  
    	  String response = "";
    	  String error = "";
    	  int _level = level;
    	  JSONObject json = new JSONObject();
    	  StringBuilder sb = new StringBuilder();
    	  //Preparing for download url
    	  sb.append(SessionConstants.LEVEL).append("=").append(level)
    	  .append("&").append(SessionConstants.SESSION_KEY).append("=").append(token)
    	  .append("&").append(SessionConstants.TYPE).append("=").append(SessionConstants.DOWNLOAD);
    	  switch(level){
	    	  case 1:
	    		JumbledWordProblem jwp = JumbledWordProblem.get();
	    		JumbledOutput jo = jwp.get(null);
		    	user.setJumbledOutput(jo);
		    	response = jo.getResult(); 
		    	JSONObject job = new JSONObject();
		    	/*job.put(Parameter.FILE_SIZE, WordProcessor.getInstance().getDictionaryFileSize());*/
		    	
		    	sb.append("&").append(Parameter.FILE_ID).append("=").append(SessionConstants.DICTIONARY_FILE_NAME);
		    	json.put(SessionConstants.DICTIONARY_DOWNLOAD_URL,
		    			SessionConstants.getDownloadURL(sb.toString()));
		    	json.put(SessionConstants.STATUS, response);
		    	break;
	    	  case 2:
	    		BitmapProblem bp = BitmapProblem.get();
	    		  BitmapOutput bo = bp.getRandomFile();
	    		  JSONObject fj = new JSONObject();
	    		  fj.put(Parameter.FILE_ID, bo.get(Parameter.FILE_ID));
	    		  fj.put(Parameter.FILE_SIZE, bo.get(Parameter.FILE_SIZE));
	    		  json.put(SessionConstants.STATUS, fj.toString());
	    		  
	    		  for(int i=0;i<SessionConstants.BITMAP_FILE_COUNT;i++){
	    			  String fuid = UUID.randomUUID().toString();
	    			  sb.append("&").append(Parameter.FILE_ID).append("=").append(fuid);
	    			  String context = SessionConstants.getDownloadURL(sb.toString());
	    			  json.accumulate(SessionConstants.DOWNLOAD_URL, context);
	    			  bo.add(fuid, null);
	    		  }
	    		
	    		 user.setBitmapOutput(bo);
	    		 break;
	    		
	    	  case 3:
	    		  
	    		  Map<String, Object> socMap = new HashMap<String, Object>();
	    		  String filename = "social"+System.currentTimeMillis()+".zip";
	    		  
	    		  socMap.put(Parameter.PERSON_NAME,"shankar");
	    		  socMap.put(Parameter.FRIEND_NAME,"mohan");
	    		  SocialOutput sop = new SocialOutput(new SocialParameter(socMap));
	    		  JSONObject jsoc = new JSONObject(socMap);
	    		  user.setSocialOutput(sop);
	    		  sb.append("&").append(Parameter.FILE_ID).append("=").append(filename);
	    		  json.accumulate(SessionConstants.DOWNLOAD_URL, SessionConstants.getDownloadURL(sb.toString()));
	    		  json.put(SessionConstants.STATUS,jsoc.toString());
	    		  /**
	    		   * It's here so that json output should not have this.
	    		   */
	    		  socMap.put(Parameter.FILE_ID, filename);
	    		  break;
	    	  case 4:
	    		  int filelistLimit = SessionConstants.MAPRED_FILECOUNT;
	    		  Map<String, Object> mapredfileMap = new HashMap<String, Object>();
	    		  String filePrefix = "mapred_";
	    		  String fName="";
		    		/*for(int i=0;i<filelistLimit;i++){*/
		    		  fName = filePrefix+System.currentTimeMillis()+".zip";
		    		  mapredfileMap.put(fName, null);
		    		  /*JSONObject jf = new JSONObject();
		    		  jf.put(Parameter.FILE_ID,fName);*/
		    		  sb.append("&").append(Parameter.FILE_ID).append("=").append(fName);
		    		  json.accumulate(SessionConstants.DOWNLOAD_URL, SessionConstants.getDownloadURL(sb.toString()));
		    		/*}*/
		    		
		    		MapRedOuput mro = new MapRedOuput(new MapredParameter(mapredfileMap));
		    		user.setMapredOutput(mro);
	    		  break;
    	  }
    	  
    	    
	    	json.put(SessionConstants.ERROR, error);
	    	json.put(SessionConstants.LEVEL, _level);
	    	json.put(SessionConstants.SESSION_KEY, user.getToken());
    	  
    	  return json.toString();
      }

}
