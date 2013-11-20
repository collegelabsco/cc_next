package com.devsquare.cc.connection;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.devsquare.cc.CCSystemException;
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
		
		int status = SessionConstants.SUCCESS;
		
		if (user != null) {
			json.put(SessionConstants.SESSION_KEY, user.getToken());
			try {
				switch (level) {
				case 1:
					String _result = result.toString();
					if (_result.length() > 2) {
						_result = _result.substring(1, _result.length() - 1);
						String res[] = _result.split(",");
						JumbledWordProblem jwp = JumbledWordProblem.get();
						Map<String, Object> jwMap = new HashMap<String, Object>();
						jwMap.put(Parameter.RESPONSE, res);
						jwMap.put(Parameter.JUMBLED_WORD, user
								.getJumbledOutput().getOriginal());
						JumbledOutput jo = jwp.validate(new JumbledParameter(
								jwMap));
						if (jo.getErrorOutput() != null) {
							response = "Error";
							status = SessionConstants.FAILURE;
						}
					} else {
						status = SessionConstants.FAILURE;
						response = "Error : Invalid response.";
					}

					break;
				case 2:
					String bitmapresult = result.toString();

					JSONObject json2 = new JSONObject(bitmapresult);

					BitmapProblem bp = BitmapProblem.get();
					Map<String, Object> subMap = new HashMap<String, Object>();
					subMap.put(Parameter.FILE_ID, json2.get(Parameter.FILE_ID));
					subMap.put(Parameter.FILE_HASH,	json2.get(Parameter.FILE_HASH));
					BitmapParameter bparam = new BitmapParameter(subMap);
					BitmapOutput bout = bp.validate(bparam);
					if (bout.getErrorOutput() != null) {
						response = "Error";
						status = SessionConstants.FAILURE;
					}

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
						sumMap.put(jos.getInt("age"), jos.getInt("count"));
					}

					Map<String, Object> mrpMap = new HashMap<String, Object>();
					mrpMap.put(Parameter.PEOPLE_AGE, sumMap);
					MapredParameter mp = new MapredParameter(mrpMap);
					mp.setMapredOrinalData(user.getMapredOutput().getOutput()
							.getOriginal());
					MapRedOuput mro = mrp.validate(mp);
					if (mro.getErrorOutput() != null) {
						response = "Error";
						status = SessionConstants.FAILURE;
					}

					break;
				}
			} catch (Exception e) {
				error = e.getMessage();
				response = "";
				Log.info(e.getMessage());
				e.printStackTrace();
			}
		} else {
			error = "User session does not exist";
			response = "";
			status = SessionConstants.NONE;
		}
		
		int score = 100;
		if(status == SessionConstants.FAILURE){
			score = 0;
		}
		
		if(status != SessionConstants.NONE){
			DBMgr.get().executeResult(sessionToken, level, score, String.valueOf(System.currentTimeMillis()));
		}
		
		json.put(SessionConstants.STATUS, response);
		json.put(SessionConstants.ERROR, error);
		json.put(SessionConstants.LEVEL, level);

		return json.toString();
	}

}
