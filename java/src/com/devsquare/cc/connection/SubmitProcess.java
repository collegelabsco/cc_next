package com.devsquare.cc.connection;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.devsquare.cc.CCSystemException;
import com.devsquare.cc.InvalidResult;
import com.devsquare.cc.db.DBMgr;
import com.devsquare.cc.interfaces.Parameter;
import com.devsquare.cc.log.Log;
import com.devsquare.cc.problem.bitmap.BitmapOutput;
import com.devsquare.cc.problem.bitmap.BitmapParameter;
import com.devsquare.cc.problem.bitmap.BitmapProblem;
import com.devsquare.cc.problem.jumbled.JumbledOutput;
import com.devsquare.cc.problem.jumbled.JumbledParameter;
import com.devsquare.cc.problem.jumbled.JumbledWordProblem;
import com.devsquare.cc.problem.mapred.MapRedOuput;
import com.devsquare.cc.problem.mapred.MapRedProblem;
import com.devsquare.cc.problem.mapred.MapredParameter;

public class SubmitProcess implements Processor {

	public String process(Object result, int level, String sessionToken)
			throws Exception {

		User user = SessionManager.getInstance().getUser(sessionToken);
		String response = "OK";
		JSONObject json = new JSONObject();
		String error = "";
		
		
		if (user != null) {
			
			
			
			json.put(SessionConstants.SESSION_KEY, user.getToken());
			int score = 0;
			try {
				if(result==null){
					throw new InvalidResult("result parameter is missing.Please check query string in your url.");
				}
				
				switch (level) {
				case 1:
					String _result = result.toString().replaceFirst("\\[", "").replaceAll("\\]","");
//					if (_result.length() > 2) {
//						_result = _result.substring(1, _result.length() - 1);
						String res[] = _result.split(",");
						JumbledWordProblem jwp = JumbledWordProblem.get();
						Map<String, Object> jwMap = new HashMap<String, Object>();
						jwMap.put(Parameter.RESPONSE, res);
						jwMap.put(Parameter.JUMBLED_WORD, user
								.getJumbledOutput().getOriginal());
						JumbledOutput jo = jwp.validate(new JumbledParameter(
								jwMap));
						score=100;
//						if (jo.getErrorOutput() != null) {
//							response = "Error";
//							score=0;
//						}
//					} else {
//						response = "Error : Invalid response.";
//						score=0;
//					}

					break;
				case 2:
					String bitmapresult = result.toString();

					JSONObject json2 = new JSONObject(bitmapresult);

					BitmapProblem bp = BitmapProblem.get();
					Map<String, Object> subMap = new HashMap<String, Object>();
					if(!json2.has(Parameter.FILE_ID) ){
						throw new InvalidResult(Parameter.FILE_ID+" attribute missing from result");
					}
					
					if(!json2.has(Parameter.FILE_HASH)){
						throw new InvalidResult(Parameter.FILE_HASH+" attribute missing from result");
					}
					subMap.put(Parameter.FILE_ID, json2.get(Parameter.FILE_ID));
					subMap.put(Parameter.FILE_HASH,	json2.get(Parameter.FILE_HASH));
					BitmapParameter bparam = new BitmapParameter(subMap);
					BitmapOutput bout = bp.validate(bparam);
					score=100;
//					if (bout.getErrorOutput() != null) {
//						response = "Error";
//						score=0;
//					}

					break;
				case 3:
					break;

				case 4:
					MapRedProblem mrp = MapRedProblem.get();
					String r4 = result.toString();
					JSONArray array = new JSONArray(r4);
					Log.info(array.toString());
					int length = array.length();
					Map<Integer, Integer> sumMap = new HashMap<Integer, Integer>();
					for (int i = 0; i < length; i++) {
						JSONObject jos = array.optJSONObject(i);
						if(!jos.has("age")){
							throw new InvalidResult("age attribute missing from result");
						}
						if(!jos.has("count")){
							throw new InvalidResult("count attribute missing from result");
						}
						
						sumMap.put(jos.getInt("age"), jos.getInt("count"));
					}

					Map<String, Object> mrpMap = new HashMap<String, Object>();
					mrpMap.put(Parameter.PEOPLE_AGE, sumMap);
					MapredParameter mp = new MapredParameter(mrpMap);
					mp.setMapredOrinalData(user.getMapredOutput().getOutput()
							.getOriginal());
					MapRedOuput mro = mrp.validate(mp);
					score=100;
//					if (mro.getErrorOutput() != null) {
//						response = "Error";
//						score=0;
//					}

					break;
				}
			} catch (Exception e) {
				
				
				if(e instanceof InvalidResult){
					response = e.getMessage();
				}else{
					error = e.getMessage();
					response = "";
				}
				
				Log.info(e.getMessage());
				e.printStackTrace();
			}

			DBMgr.get().executeResult(sessionToken, level, score, String.valueOf(System.currentTimeMillis()));
			
		} else {
			error = "User session does not exist";
			response = "Error";
		}
		
		json.put(SessionConstants.STATUS, response);
		json.put(SessionConstants.ERROR, error);
		json.put(SessionConstants.LEVEL, level);

		return json.toString();
	}

}
