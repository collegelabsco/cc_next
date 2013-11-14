package com.devsquare.cc.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.channels.Channel;
import java.util.HashMap;
import java.util.Map;

import com.devsquare.cc.InvalidRequest;
import com.devsquare.cc.log.Log;

public class Event implements Runnable {

	Socket clientSocket = null;
	DataInputStream is = null;
	DataOutputStream writer = null;
	Channel channel = null;

	public Event(Socket socket) throws IOException {
		this.clientSocket = socket;
		is = new DataInputStream(socket.getInputStream());
		writer = new DataOutputStream(socket.getOutputStream());
		channel = socket.getChannel();
	}
	
	

	@Override
	public void run() {

		try {
			
			    //clientSocket.shutdownInput();
				process();

		}  catch (InvalidRequest r) {
			Log.info("Invalid request from user." + r.getMessage());
			write(r.getMessage());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Log.info("uns Invalid request from user. "+e.getMessage());
			write("Invalid request from user.");
		} catch (IOException e) {
			e.printStackTrace();
			write("Bad request");
		} catch (Exception e) {
			write("Something wrong at server :"+e.getMessage());
		} finally {
			try {
				clientSocket.shutdownOutput();
				Thread t = new Thread(){
					public void run(){
						try {
							while(is.read()!=-1);
							Log.info("Socket closed from client.");
							synchronized (clientSocket) {
								clientSocket.notify();
							}
							
						} catch (IOException e) {
							//e.printStackTrace();
						}
						
					}
				};
				t.setDaemon(true);
				t.start();
				close();
				
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}
	
	protected void process() throws Exception{
		String qString = is.readLine();
		clientSocket.shutdownInput();
		Map<String, String> requestParams = getQueryMap(qString);
		validateRequest(requestParams, qString);
		String type = requestParams.get(SessionConstants.TYPE);
		String response = null;
		if (type.equals("get")) {
			String levelStr = requestParams.get(SessionConstants.LEVEL);
			String sessionToken = requestParams.get(SessionConstants.SESSION_KEY);
			int level = Integer.parseInt(levelStr);
			GetProcess process = new GetProcess();
			response = process.prepareResponse(level, sessionToken);
			
		} else {
			
		}
		
		Log.info("Response :"+response);
		write(response);
	}
	
	private void close() throws IOException, InterruptedException{
		   synchronized (clientSocket) {
			clientSocket.wait(waitTime());
			Log.info("Closing socket..");
			clientSocket.close();
			is = null;
			writer = null;
		}
	}
	
	protected int waitTime(){
		return 10000;
	}
	
	
	public void write(String text){
		
		try {
			writer.writeBytes(text+"\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void validateRequest(Map<String, String> reqParams, String qstring) {

		String sessionToken = reqParams.get(SessionConstants.SESSION_KEY);
		if (sessionToken != null) {
			// TODO: validate from DB;
			String type = reqParams.get(SessionConstants.TYPE);
			if (type != null
					&& (type.equals(SessionConstants.GET) || type
							.equals(SessionConstants.SUBMIT))) {
				String level = reqParams.get(SessionConstants.LEVEL);
				if (level != null) {
					try {
						int l = Integer.parseInt(level);
						if ((l == 1 || l == 2 || l == 3 || l == 4) == false) {
							throw new NumberFormatException();
						}
					} catch (NumberFormatException e) {
						throw new InvalidRequest(
								"level value should be 1/2/3/4 only." + qstring);
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

	}

	protected Map<String, String> getQueryMap(String query) {
		String[] params = query.split("&");
		Map<String, String> map = new HashMap<String, String>();
		for (String param : params) {
			String tokens[] = param.split("=");
			if (tokens.length == 2) {
				map.put(tokens[0], tokens[1]);
			} else {
				throw new InvalidRequest("Not a proper query string." + query);
			}
		}
		return map;
	}
	
}
