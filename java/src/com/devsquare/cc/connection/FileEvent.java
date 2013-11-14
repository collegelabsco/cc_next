package com.devsquare.cc.connection;

import java.io.IOException;
import java.net.Socket;
import java.nio.channels.WritableByteChannel;
import java.util.Map;

import com.devsquare.cc.InvalidRequest;
import com.devsquare.cc.log.Log;
import com.devsquare.cc.mgr.UserManager;
import com.devsquare.cc.problem.jumbled.WordProcessor;

public class FileEvent extends Event {

	public FileEvent(Socket socket) throws IOException {
		super(socket);
	}
	
	
	@Override
	public void process() throws IOException{
		
		String qString = is.readLine();
		Log.info("User request = "+qString);
		Map<String, String> requestParams = getQueryMap(qString);
		validateRequest(requestParams, qString);
	//	String fileName = requestParams.get(SessionConstants.FILENAME);
		for(int i=0;i<2;i++){
			switch(i){
			case 0:
				write("filesize="+WordProcessor.getInstance().getDictionaryFileSize());
			case 1:
				WordProcessor.getInstance().streamDictionaryFile((WritableByteChannel)channel);
			}
		}
		
	}
	
	public int waitTime(){
		return 30000;
	}
	
	public void write(byte[] b) throws IOException{
		writer.write(b);
		writer.flush();
	}
	
	public void validateRequest(Map<String, String> reqParams, String qstring) {

		String sessionToken = reqParams.get(SessionConstants.SESSION_KEY);
		User u = UserManager.getMgr().getUser(sessionToken);
		if (sessionToken != null && u!=null) {
			// TODO: validate from DB;
			
			String type = reqParams.get(SessionConstants.TYPE);
			if (type != null
					&& type.equals(SessionConstants.DOWNLOAD)) {
				String level = reqParams.get(SessionConstants.LEVEL);
				if (level != null) {
					try {
						int l = Integer.parseInt(level);
						if ((l == 1 || l == 2 || l == 3 || l == 4) == false) {
							throw new NumberFormatException();
						}
						String fileName = reqParams.get(SessionConstants.FILENAME);
						if(fileName==null){
							throw new InvalidRequest("File name is missing from request.");
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
								+ SessionConstants.DOWNLOAD +"." + qstring);
			}

		} else {
			throw new InvalidRequest(
					"sessionkey parameter missing from request or token is no longer vaid. " + qstring);
		}

	}

}
