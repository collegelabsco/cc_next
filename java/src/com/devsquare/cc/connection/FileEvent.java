package com.devsquare.cc.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.devsquare.cc.InvalidRequest;
import com.devsquare.cc.log.Log;
import com.devsquare.cc.mgr.UserManager;
import com.devsquare.cc.problem.jumbled.WordProcessor;

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
	public void process() throws IOException {

		String qString = req.getQueryString();
		Log.info("User request = " + qString);
		requestParams = getQueryMap(qString);
		//validateRequest(requestParams, qString);
		
		write();
		
	}

	public int waitTime() {
		return 30000;
	}

	public void write() {
		InputStream is = null;
		try {
			is = WordProcessor.getInstance().getDictionaryStream();
			res.addHeader(HTTP_HEADER_CONTENT_DISPOSITION, 
					CONTENT_DISPOSITION_ATTACHMENT + 
					CONTENT_DISPOSITION_ATTACHMENT_FILENAME + "\"" + requestParams.get(SessionConstants.FILENAME) + "\";");	
			
			IOUtils.copy(is, res.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
		}

	}

	public void validateRequest(Map<String, String> reqParams, String qstring) {

		String sessionToken = reqParams.get(SessionConstants.SESSION_KEY);
		User u = UserManager.getMgr().getUser(sessionToken);
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
						String fileName = reqParams
								.get(SessionConstants.FILENAME);
						if (fileName == null) {
							throw new InvalidRequest(
									"File name is missing from request.");
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
