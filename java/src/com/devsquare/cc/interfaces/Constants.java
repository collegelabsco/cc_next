package com.devsquare.cc.interfaces;

import java.io.File;

public class Constants {


	final static int LEVEL_1 = 1;
	final static int LEVEL_2 = 2;
	final static int LEVEL_3 = 3;
	final static int LEVEL_4 = 4;

	public final static int LOG_LEVEL = 1;

	// ERROR_CODES
	final static int INVALID_REQUEST = 1;
	public final static int SYSTEM_ERROR = 2;

	// Default data dir location

	public final static String DATA_DIR = fetchDataDir();
	
	public final static String MAPRED_DIR="mapred";
	public static final int MAPRED_LIMIT = 50000;
	public static final int CONNECTION_LIMIT = 10000;
	
	private static String fetchDataDir() {
		String path = System.getProperty("data.dir");
		if(path==null){
			path = Constants.class.getResource("/").getPath();
			File bin = new File(path);
			path = bin.getAbsolutePath()+"/data";
		}
		
		return path;
	}
	
}
