package com.devsquare.cc.connection;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.devsquare.cc.InvalidRequest;
import com.devsquare.cc.db.DBMgr;
import com.devsquare.cc.interfaces.Output;
import com.devsquare.cc.log.Log;

public class Event implements Runnable {

	/*Socket clientSocket = null;
	DataInputStream is = null;
	DataOutputStream writer = null;
	Channel channel = null;
*/
	/*public Event(Socket socket) throws IOException {
		this.clientSocket = socket;
		is = new DataInputStream(socket.getInputStream());
		writer = new DataOutputStream(socket.getOutputStream());
		channel = socket.getChannel();
	}*/
	
	HttpServletRequest req = null;
	HttpServletResponse res = null;
	

	public Event(HttpServletRequest request, HttpServletResponse response) {
		this.req = request;
		this.res = response;
	}



	@Override
	public void run() {

		try {
			
			    //clientSocket.shutdownInput();
				process();

		}  catch (InvalidRequest r) {
			Log.info("Invalid request from user." + r.getMessage());
			r.printStackTrace();
			write(r.getMessage());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Log.info("uns Invalid request from user. "+e.getMessage());
			write("Invalid request from user.");
		} catch (IOException e) {
			e.printStackTrace();
			write("Bad request");
		} catch (Exception e) {
			e.printStackTrace();
			write("Something wrong at server :"+e.getMessage());
			
		} 
	}
	
	protected void process() throws Exception{
		String qString = req.getQueryString();
		Map<String, String> requestParams = getQueryMap(qString);
		System.out.println(req.getParameter(SessionConstants.RESULT));
		
		validateRequest(requestParams, qString);
		String type = requestParams.get(SessionConstants.TYPE);
		String response = null;
		String levelStr = requestParams.get(SessionConstants.LEVEL);
		String sessionToken = requestParams.get(SessionConstants.SESSION_KEY);
		int level = Integer.parseInt(levelStr);
		
		if (type.equals("get")) {
			GetProcess process = new GetProcess();
			response = process.prepareResponse(level, sessionToken);
		} else {
			String result = requestParams.get(SessionConstants.RESULT);
			Log.info("Result..."+result);
			SubmitProcess sProcess = new SubmitProcess();
			response = sProcess.process(result,level,sessionToken);
			
		}
		
		Log.info("Response :"+response);
		write(response);
	}
	
	
	public void write(String text){
		try {
			res.getWriter().write(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void validateRequest(Map<String, String> reqParams, String qstring) throws Exception {

		String sessionToken = reqParams.get(SessionConstants.SESSION_KEY);
		User u = SessionManager.getInstance().getUser(sessionToken);
		int l = -1;
		String type;
		if (sessionToken != null) {
			// TODO: validate from DB;
			if(u==null){
				u = DBMgr.get().fetchUser(sessionToken);
				if(u!=null){
					SessionManager.getInstance().addUser(sessionToken, u);
				}else{
					throw new InvalidRequest("Invalid session. User is not mapped with sessionkey : "+sessionToken);
				}
			}
			
			type = reqParams.get(SessionConstants.TYPE);
			if (type != null
					&& (type.equals(SessionConstants.GET) || type
							.equals(SessionConstants.SUBMIT))) {
				String level = reqParams.get(SessionConstants.LEVEL);
				if (level != null) {
					try {
						 l = Integer.parseInt(level);
						if ((l == 1 || l == 2 || l == 3 || l == 4) == false) {
							throw new NumberFormatException();
						}
					} catch (NumberFormatException e) {
						throw new InvalidRequest(
								"level value should be 1/2/3/4 only." + qstring);
					}
					if(type.equals(SessionConstants.SUBMIT)){
						Output<?> requestGenerater = null;
						switch(l){
						case 1:
							requestGenerater = u.getJumbledOutput();
							break;
						case 2:
							requestGenerater = u.getBitmapOutput();
							break;
						case 3:
							requestGenerater = u.getSocialOutput();
							break;
						case 4:
							requestGenerater = u.getMapredOutput();
							break;
						}
						
						if(requestGenerater==null){
							throw new InvalidRequest(
									"No problem was selected before submiting. Please get the for problem first.");
						}
					}

				} else {
					throw new InvalidRequest(
							"level parameter is missing from request.");
				}

			} else {
				throw new InvalidRequest(
						"Either type parameter is missing or value of type parameter is not of"
								+ SessionConstants.GET + "/"
								+ SessionConstants.SUBMIT + "." + qstring);
			}

		} else {
			throw new InvalidRequest(
					"sessionkey parameter missing from request. " + qstring);
		}
		
		DBMgr.get().executeLoggerInsert(u.getEmail(), u.getToken(), l,type, String.valueOf(System.currentTimeMillis()));

	}

	protected Map<String, String> getQueryMap(String query) {
		String[] params = query.split("&");
		Map<String, String> map = new HashMap<String, String>();
		for (String param : params) {
			String tokens[] = param.split("=");
			if (tokens.length == 2) {
				map.put(tokens[0].trim(), tokens[1].trim());
			} else {
				throw new InvalidRequest("Not a proper query string." + query);
			}
		}
		return map;
	}
	
}
