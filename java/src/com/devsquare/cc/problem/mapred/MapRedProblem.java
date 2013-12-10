package com.devsquare.cc.problem.mapred;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.devsquare.cc.InvalidResult;
import com.devsquare.cc.interfaces.Constants;
import com.devsquare.cc.interfaces.Parameter;
import com.devsquare.cc.interfaces.Problem;
import com.devsquare.cc.log.Log;

public class MapRedProblem implements Problem<MapRedOuput, MapredParameter> {
	
	
	RandomAccessFile raf = null;
	List<String> lines = null;
	Random ranGen = null;
	
	private static MapRedProblem SINGLETON = new MapRedProblem();
	
	public static MapRedProblem get(){
		return SINGLETON;
	}
	
	public MapRedProblem init() throws IOException{
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

	@Override
	public MapRedOuput validate(MapredParameter parameter) {
		
		Map<Integer,Integer> ageGroup = parameter.getPeopleCountWithAge();
		Iterator<Entry<Integer, Integer>> paItr = ageGroup.entrySet().iterator();
		MapredOriginalData mod = parameter.getOriginal();
		Map<String, Object> outputMap = new HashMap<String, Object>();
		while(paItr.hasNext()){
			Entry<Integer, Integer> entry = paItr.next();
			if(mod.getPeopleAgeGroup().get(entry.getKey())!=
					entry.getValue()){
				throw new InvalidResult("Invalid count for age "+entry.getKey());
			}
		}
		
		return new MapRedOuput(new MapredParameter(outputMap));
	}
	
	private void createLineList() throws IOException{
		lines = new LinkedList<String>();
		String line = null;
		while((line=raf.readLine())!=null){
		   lines.add(line);	
		}
		raf.close();
	}
	
	
	
	public void readFile(MapredParameter parameter) throws IOException{
		
		OutputStream os = parameter.getOutputStream();
		int limit =  Constants.MAPRED_LIMIT;
		Map<Integer, Integer> peopleAgeGroup= parameter.getOriginal().getPeopleAgeGroup();
		while(limit-->0){
			int lineIndex = ranGen.nextInt(lines.size()-1);
			String line = lines.get(lineIndex);
			int age = ranGen.nextInt(5)+1;
			String _li = line+"|"+age+"\n";
			IOUtils.write(_li, os);
			int count = 1;
			if(peopleAgeGroup.containsKey(age)){
				count = peopleAgeGroup.get(age)+1;
			}
			peopleAgeGroup.put(age, count);
			
		}
		
		JSONObject ja = new JSONObject(peopleAgeGroup);
		Log.debug("expected "+ja.toString());
		
	}

}
