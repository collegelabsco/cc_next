package com.devsquare.cc.connection;

import java.util.Properties;

import com.devsquare.cc.interfaces.Constants;

public class SessionConstants {
	
	public static final String SESSION_KEY = "sessionkey";
	public static final String LEVEL = "level";
	public static final String TYPE = "type";
	
	public static final String GET="get";
	public static final String SUBMIT="submit";
	public static final String DOWNLOAD = "download";
//	public static final String FILENAME = "filename";
	public static final int MAPRED_FILECOUNT = 5;
	public static final String RESULT = "result";
	public static final String DICTIONARY_FILE_NAME = "dictionary.zip";
	public static final int BITMAP_FILE_COUNT = 3;
	public static final int SUCCESS = 1;
	public static final int FAILURE = -1;
	public static final int NONE = 0;
	public static final String DOWNLOAD_URL = "downloadURL";
	public static final String ERROR = "Error";
	public static final String STATUS = "status";
	public static final String DICTIONARY_DOWNLOAD_URL = "dictionaryDownloadURL";
	
	private static Properties properties = null;
	
	public static void setProperties(Properties prop){
		properties = prop;
	}
	
	public static String getValue(String key){
		return properties.getProperty(key);
	}
	
	public static int getMapRedFileCount(){
		return Integer.valueOf(properties.getProperty("MAPRED_FILE_COUNT", "10"));
	}
	
	public static String getDownloadURL(String queryString){
		StringBuilder sb = new StringBuilder();
		sb.append("http://")
		  .append(properties.get("host"))
		  .append(":")
		  .append(properties.get("port"))
		  .append("/cc/d?")
		  .append(queryString);
		
		return sb.toString();
	}
	
	public static int getBitmapFileCount(){
		return Integer.valueOf(properties.getProperty("BITMAP_FILE_COUNT", "3"));
	}

	public static long getAllowedTimeForQuestionSubmittion(int level){
		long value = 0;
		switch(level){
		case Constants.LEVEL_1:
			 value = Integer.valueOf(properties.getProperty("level_1_limit"));
			 break;
		case Constants.LEVEL_2:
			 value  = Integer.valueOf(properties.getProperty("level_2_limit"));
			 break;
		case Constants.LEVEL_3:
			value = Integer.valueOf(properties.getProperty("level_3_limit"));
			break;
		case Constants.LEVEL_4:
			value = Integer.valueOf(properties.getProperty("level_4_limit"));
			break;
		}
		return value;
		
	}

	public static int getMapredLimit() {
		// TODO Auto-generated method stub
		return Integer.valueOf(properties.getProperty("MAPRED_LIMIT", "500"));
	}

}
