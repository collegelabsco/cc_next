package com.devsquare.cc.connection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.devsquare.cc.InvalidRequest;
import com.devsquare.cc.interfaces.Output;
import com.devsquare.cc.interfaces.Parameter;
import com.devsquare.cc.log.Log;
import com.devsquare.cc.problem.bitmap.BitmapOutput;
import com.devsquare.cc.problem.bitmap.BitmapParameter;
import com.devsquare.cc.problem.bitmap.BitmapProblem;
import com.devsquare.cc.problem.jumbled.WordProcessor;
import com.devsquare.cc.problem.mapred.MapRedOuput;
import com.devsquare.cc.problem.mapred.MapRedProblem;
import com.devsquare.cc.problem.mapred.MapredParameter;
import com.devsquare.cc.problem.social.SocialParameter;
import com.devsquare.cc.problem.social.SocialProblem;

public class FileEvent extends Event {
	
	public static final String HTTP_HEADER_CONTENT_DISPOSITION = "Content-Disposition";
    public static final String CONTENT_DISPOSITION_ATTACHMENT = "attachment; ";
    public static final String CONTENT_DISPOSITION_INLINE = "inline; ";
    public static final String CONTENT_DISPOSITION_ATTACHMENT_FILENAME = "filename=";
    
    Map<String, String> requestParams = null;

	public FileEvent(HttpServletRequest request, HttpServletResponse response) {

		super(request, response);
	}

	@Override
	public void process() throws Exception {

		String qString = req.getQueryString();
		Log.info("User request = " + qString);
		requestParams = getQueryMap(qString);
		validateRequest(requestParams, qString);
		
		String levelStr = requestParams.get(SessionConstants.LEVEL);
		String sessionToken = requestParams.get(SessionConstants.SESSION_KEY);
		int level = Integer.parseInt(levelStr);
		User user = SessionManager.getInstance().getUser(sessionToken);
		switch(level){
			case 1:
				String fileName = requestParams.get(Parameter.FILE_ID);
				if(fileName.equals(SessionConstants.DICTIONARY_FILE_NAME)){
					setResponseHeader();
					write( WordProcessor.getInstance().getDictionaryStream());
				}else{
					write("{Error:Invalid file request}");	
				}
				break;
			case 2:
				String file = requestParams.get(Parameter.FILE_ID);
				BitmapOutput bp = user.getBitmapOutput();
				if(bp.has(file)){
					BitmapProblem bprob = BitmapProblem.get();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put(Parameter.POSITION, Integer.valueOf(requestParams.get(Parameter.POSITION)));
					map.put(Parameter.LENGTH, Integer.valueOf(requestParams.get(Parameter.LENGTH)));
					map.put(Parameter.FILE_ID, bp.get(Parameter.FILE_ID));
					BitmapParameter bpm = new BitmapParameter(map);
					BitmapOutput bop =bprob.getFileChunk(bpm);
					byte[] b = (byte[])bop.get(Parameter.BYTE);
					setResponseHeader();
					write( new ByteArrayInputStream(b));
					
				}else{
					write("{Error:Invalid file request}");
				}
				
				break;
			case 3:
				String f3 = requestParams.get(Parameter.FILE_ID);
				String pName = requestParams.get(Parameter.PERSON_NAME);
				String sfile = (String) user.getSocialOutput().getParameter().getFileName();
				String friendName = (String) user.getSocialOutput().getParameter().getPersonName();
				if(friendName!=null && friendName.equals(pName) && sfile!=null & sfile.equals(f3)){
					
					Log.debug("friendName : "+friendName);
					SocialProblem sp = SocialProblem.get();
					SocialParameter sparam = user.getSocialOutput().getParameter();
					sparam.setOutputStream(res.getOutputStream());
					setResponseHeader();
					sp.getSocialNetworkFile(sparam);
					
				}else{
					write("{Error:Invalid file request}");
				}
				
				break;
			case 4:
				String f4 = requestParams.get(Parameter.FILE_ID);
				if(user.getMapredOutput().getOutput().keyExists(f4)){
					MapRedProblem mrp = MapRedProblem.get();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					MapRedOuput mro = user.getMapredOutput();
					MapredParameter mp = mro.getOutput();
					mp.setOutputStream(baos);
					mrp.readFile(mp);
					//user.setMapredOutput(mp.getOriginal().getPeopleAgeGroup());
					baos.size();
					ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
					baos.close();
					setResponseHeader();
					write(bais);
				}else{
					write("{Error:Invalid file request}");	
				}
				
				break;
		}
		
	}

	
	public int waitTime() {
		return 30000;
	}
	
	private void setResponseHeader(){
		res.addHeader(HTTP_HEADER_CONTENT_DISPOSITION, 
				CONTENT_DISPOSITION_ATTACHMENT + 
				CONTENT_DISPOSITION_ATTACHMENT_FILENAME + "\"" + requestParams.get(Parameter.FILE_ID) + "\";");
	}

	public void write(InputStream is) {
		try {
			IOUtils.copy(is, res.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
		}

	}

	public void validateRequest(Map<String, String> reqParams, String qstring) {

		String sessionToken = reqParams.get(SessionConstants.SESSION_KEY);
		User u = SessionManager.getInstance().getUser(sessionToken);
		if (sessionToken != null && u != null) {
			// TODO: validate from DB;

			String type = reqParams.get(SessionConstants.TYPE);
			if (type != null && type.equals(SessionConstants.DOWNLOAD)) {
				String level = reqParams.get(SessionConstants.LEVEL);
				if (level != null) {
					try {
						int l = Integer.parseInt(level);
						if ((l == 1 || l == 2 || l == 3 || l == 4) == false) {
							throw new NumberFormatException();
						}
						
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
									"File request is not generated. Please get the for problem first.");
						}
						
						String fileName = reqParams
								.get(Parameter.FILE_ID);
						if (fileName == null) {
							throw new InvalidRequest(
									"File id is missing from request.");
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
								+ SessionConstants.DOWNLOAD + "." + qstring);
			}

		} else {
			throw new InvalidRequest(
					"sessionkey parameter missing from request or token is no longer vaid. "
							+ qstring);
		}

	}

}
