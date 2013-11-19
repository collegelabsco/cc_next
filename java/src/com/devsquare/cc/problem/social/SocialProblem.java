package com.devsquare.cc.problem.social;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.devsquare.cc.interfaces.Constants;
import com.devsquare.cc.interfaces.Problem;

public class SocialProblem implements Problem<SocialOutput, SocialParameter>{
	
	
	private static SocialProblem SINGLETON = new SocialProblem();
	
	RandomAccessFile raf= null;
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
		
		raf = new RandomAccessFile(file[0], "r");
		createLineList();
		ranGen = new Random();
		return this;
	}
	
	private void createLineList() throws IOException{
		lines = new LinkedList<String>();
		String line = null;
		while((line=raf.readLine())!=null){
		   lines.add(line);	
		}
	}


	public static SocialProblem get(){
		return SINGLETON;
	}
	
	public SocialOutput getSocialNetworkFile(){
		SocialOutput sop = new SocialOutput(null);
		
		
		
		return null;
	}

	@Override
	public SocialOutput validate(SocialParameter parameter) {

		return null;
	}
	
	

}
