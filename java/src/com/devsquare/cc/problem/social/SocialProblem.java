package com.devsquare.cc.problem.social;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.devsquare.cc.interfaces.Constants;
import com.devsquare.cc.interfaces.Problem;
import com.devsquare.cc.log.Log;
import com.devsquare.cc.problem.AbstractProblem;

public class SocialProblem extends AbstractProblem implements Problem<SocialOutput, SocialParameter>{
	
	
	private static SocialProblem SINGLETON = new SocialProblem();
	
	Random ranGen = null;
	List<String> lines = new LinkedList<String>();
	
	public SocialProblem init() throws IOException{
		String mapred_dir = Constants.DATA_DIR+"/"+Constants.MAPRED_DIR;
		File mapreddir = new File(mapred_dir);
		if(!mapreddir.exists()){
			throw new IllegalStateException("Mapred dir "+mapreddir+" does not exist.");
		}
		
		File file[] = mapreddir.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				return !pathname.isDirectory();
			}
		});
		
		if(file.length==0){
			throw new IllegalStateException("Mapred file is not present in dir "+mapreddir+".");
		}
		
		createLineList(file[0]);
		ranGen = new Random();
		return this;
	}
	
	private void createLineList(File file) throws IOException{
		FileInputStream fis = new FileInputStream(file);
		try{
		lines = IOUtils.readLines(fis);
		}finally{
			IOUtils.closeQuietly(fis);
		}
		
	}


	public static SocialProblem get(){
		return SINGLETON;
	}
	
	public void getSocialNetworkFile(SocialParameter parameter) throws IOException{
		String person = parameter.getPersonName();
		OutputStream os = parameter.getOutputStream();
		int limit =  Constants.CONNECTION_LIMIT;
		while(limit-->0){
			int lineIndex = ranGen.nextInt(lines.size()-1);
			String line = lines.get(lineIndex).concat("\n");
			IOUtils.write(line, os);
		}
	}

	@Override
	public SocialOutput validate(SocialParameter parameter) {

		return null;
	}
	
	

}
