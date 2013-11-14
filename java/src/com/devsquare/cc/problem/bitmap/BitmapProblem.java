package com.devsquare.cc.problem.bitmap;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.devsquare.cc.interfaces.Constants;
import com.devsquare.cc.interfaces.Problem;
import com.devsquare.cc.problem.bitmap.interfaces.IReader;

public class BitmapProblem implements Problem<BitmapOutput, BitmapParameter> {
	
	
	
	private Map<String, IReader> readerMap = new HashMap<String, IReader>();
	
	Random randomGen = null;
	
	public BitmapProblem() throws IOException{
		
		String dataDir = Constants.DATA_DIR;
		File f  = new File(dataDir);
		File[] farray = f.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				String name = pathname.getName();
				return (name.endsWith("jpg") || name.endsWith("jpeg"));
			}
		});
		
		for(File ff:farray){
			readerMap.put(ff.getName(), new BitmapFileChannelReader(ff));
		}
		
		randomGen = new Random();
		
	}
	
	

	public BitmapOutput getFileChunk(BitmapParameter parameter) throws Exception {
		int position = parameter.getPosition();
		int length = parameter.getLength();
		String fileId = parameter.getFileID();
		IReader reader = readerMap.get(fileId);
		byte b[] = reader.read(position, length);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(BitmapParameter.BYTE, b);
		BitmapOutput bop = new BitmapOutput(map);
		return bop;
	}
	
	public BitmapOutput getRandomFile(){
		int index = randomGen.nextInt(readerMap.size());
		Entry<String, IReader> entry = (Entry<String, IReader>)readerMap.entrySet().toArray()[index];
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(BitmapParameter.FILE_ID, entry.getKey());
		map.put(BitmapParameter.FILE_SIZE, entry.getValue().size());
		
		return new BitmapOutput(map);
	}

	@Override
	public BitmapOutput validate(BitmapParameter parameter) {
		
		return null;
	}

}
