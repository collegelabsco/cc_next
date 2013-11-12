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

import com.devsquare.cc.interfaces.Constants;
import com.devsquare.cc.interfaces.Parameter;
import com.devsquare.cc.interfaces.Problem;

public class MapRedProblem implements Problem<MapRedOuput, MapredParameter> {
	
	
	RandomAccessFile raf = null;
	List<String> lines = null;
	Random ranGen = null;
	
	public MapRedProblem() throws IOException{
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
	}

	@Override
	public MapRedOuput validate(MapredParameter parameter) {
		
		Map<Integer,Integer> ageGroup = parameter.getPeopleCountWithAge();
		Iterator<Entry<Integer, Integer>> paItr = ageGroup.entrySet().iterator();
		MapredOriginalData mod = parameter.getOriginal();
		Map<String, Object> outputMap = new HashMap<>();
		while(paItr.hasNext()){
			Entry<Integer, Integer> entry = paItr.next();
			if(mod.getPeopleAgeGroup().get(entry.getKey())!=
					entry.getValue()){
				outputMap.put(Parameter.ERROR_OUTPUT, "Number of persons of age "+entry.getKey()+" is not correct");
			}
			
		}
		
		return new MapRedOuput(outputMap);
	}
	
	private void createLineList() throws IOException{
		lines = new LinkedList<String>();
		String line = null;
		while((line=raf.readLine())!=null){
		   lines.add(line);	
		}
	}
	
	
	public void readFile(MapredParameter parameter) throws IOException{
		
		OutputStream os = parameter.getOutputStream();
		int limit = Constants.MAPRED_LIMIT;
		Map<Integer, Integer> peopleAgeGroup= parameter.getOriginal().getPeopleAgeGroup();
		while(limit-->0){
			int lineIndex = ranGen.nextInt(lines.size()-1);
			String line = lines.get(lineIndex);
			int age = ranGen.nextInt(100);
			String _li = line+"|"+age+"\n";
			os.write(_li.getBytes());
			os.flush();
			int count = 1;
			if(peopleAgeGroup.containsKey(age)){
				count = peopleAgeGroup.get(age)+1;
			}
			peopleAgeGroup.put(age, count);
			
		}
		
		
	}

}
