package com.devsquare.cc.connection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.devsquare.cc.InvalidRequest;
import com.devsquare.cc.db.DBMgr;
import com.devsquare.cc.interfaces.Callback;
import com.devsquare.cc.interfaces.Output;
import com.devsquare.cc.interfaces.Parameter;
import com.devsquare.cc.log.Log;
import com.devsquare.cc.mgr.DataTransferMgr;
import com.devsquare.cc.mgr.intrfaces.SetInput;
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
		final User user = SessionManager.getInstance().getUser(sessionToken);
		switch(level){
			case 1:
				String fileName = requestParams.get(Parameter.FILE_ID);
				if(fileName.equals(SessionConstants.DICTIONARY_FILE_NAME)){
					setResponseHeader();
					//write( WordProcessor.getInstance().getDictionaryStream());
					DataTransferMgr.get().getZipStream(res.getOutputStream()).setInputStream(new SetInput() {
						boolean inputAvailabel = true;
						@Override
						public void process(ZipOutputStream zos) throws IOException {
								InputStream is = null;
								try{
									is = WordProcessor.getInstance().getDictionaryStream();
								    IOUtils.copy(is, zos);
								}finally{
									if(is!=null){
										IOUtils.closeQuietly(is);
									}
								}
								inputAvailabel=false;
						}
						
						@Override
						public boolean isInputAvailable() {
							return inputAvailabel;
						}
					}).process();
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
//				String pName = requestParams.get(Parameter.PERSON_NAME);
				String sfile = (String) user.getSocialOutput().getParameter().getFileName();
//				String friendName = (String) user.getSocialOutput().getParameter().getPersonName();
				if( sfile!=null & sfile.equals(f3)){
					setResponseHeader();
					final SocialProblem sp = SocialProblem.get();
					final SocialParameter sparam = user.getSocialOutput().getParameter();
					DataTransferMgr.get().getZipStream(res.getOutputStream()).setInputStream(new SetInput() {
						int count=10;
						@Override
						public void process(ZipOutputStream zos) throws IOException {
							sparam.setOutputStream(zos);
							sp.getSocialNetworkFile(sparam);
							count--;
						}
						
						@Override
						public boolean isInputAvailable() {
							return count>0;
						}
					}).process();
					
					
					
				}else{
					write("{Error:Invalid file request}");
				}
				
				break;
			case 4:
				String f4 = requestParams.get(Parameter.FILE_ID);
				if(user.getMapredOutput().getOutput().keyExists(f4)){
					final MapRedProblem mrp = MapRedProblem.get();
					MapRedOuput mro = user.getMapredOutput();
					final MapredParameter mp = mro.getOutput();
					setResponseHeader();
					DataTransferMgr.get().getZipStream(res.getOutputStream()).setInputStream(new SetInput() {
						
						int count = 80;
						
						@Override
						public void process(ZipOutputStream zos) throws IOException {
							mp.setOutputStream(zos);
							mrp.readFile(mp);
							count--;
						}
						
						@Override
						public boolean isInputAvailable() {
							// TODO Auto-generated method stub
							return count>0;
						}
					}).process();
					//user.setMapredOutput(mp.getOriginal().getPeopleAgeGroup());
					
					//write(bais);
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
	
	public void writeZip(InputStream is) {
		try {
			
			IOUtils.copy(is, res.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
		}

	}


	public void validateRequest(Map<String, String> reqParams, String qstring) throws Exception {

		String sessionToken = reqParams.get(SessionConstants.SESSION_KEY);
		User u = SessionManager.getInstance().getUser(sessionToken);
		int l = -1;
		String type = null;
		if (sessionToken != null && u != null) {

			type = reqParams.get(SessionConstants.TYPE);
			if (type != null && type.equals(SessionConstants.DOWNLOAD)) {
				String level = reqParams.get(SessionConstants.LEVEL);
				if (level != null) {
					try {
						 l = Integer.parseInt(level);
						if ((l == 1 || l == 2 /*|| l == 3 */|| l == 4) == false) {
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
								"level value should be 1/2/4 only. " + qstring);
					}

				} else {
					throw new InvalidRequest(
							"level parameter is missing from request.");
				}

			} else {
				throw new InvalidRequest(
						"Either type parameter is missing or value of type parameter is not of "
								+ SessionConstants.DOWNLOAD + ". " + qstring);
			}

		} else {
			throw new InvalidRequest(
					"sessionkey parameter missing from request or token is no longer vaid. "
							+ qstring);
		}
		
		DBMgr.get().executeLoggerInsert(u.getEmail(), u.getToken(), l, type, String.valueOf(System.currentTimeMillis()));

	}

}
