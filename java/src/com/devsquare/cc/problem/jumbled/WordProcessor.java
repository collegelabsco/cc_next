package com.devsquare.cc.problem.jumbled;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.devsquare.cc.CCSystemException;
import com.devsquare.cc.interfaces.Constants;
import com.devsquare.cc.log.Log;

public class WordProcessor {

	private static WordProcessor SINGLETON = null;

	private Object mapObject = new Object();

	private final Map<String, Object> wordMap = new HashMap<String, Object>();

	private final List<String> wordList = new ArrayList<String>();

	Random randomGen = new Random();

	private long dfl;
	
	private String filePath = null;
	
	URL url = null;

	// a [closed%3:00:04::] [closed] shut, unopen

	private WordProcessor() {

	}

	public static WordProcessor getInstance() {
		if(SINGLETON == null){
			SINGLETON = new WordProcessor();
		}
		return SINGLETON;
	}
	
	public long getDictionaryFileSize(){
		return dfl;
	}
	
	public InputStream getDictionaryStream() throws IOException{
		return url.openStream();
	}
	
	
//	public void streamDictionaryFile(WritableByteChannel channel) throws IOException{
//		FileInputStream fis = new FileInputStream(filePath);
//		FileChannel fc = fis.getChannel();
//		long position = 0;
//		long length = dfl;
//		while(true){
//			long read = fc.transferTo(position, length, channel);
//			length = length-read;
//			if(length==0){
//				break;
//			}
//			position=position+read;
//		}
//		
//		fc.close();
//		fis.close();
//		//new FileInputStream(filePath).getChannel().transferTo(0, dfl,channel );
//	}

	public WordProcessor init() throws CCSystemException{
		
		url = WordProcessor.class.getResource("core-wordnet.txt");
		RandomAccessFile raf = null;
		try {
			filePath = url.getFile();
			raf = new RandomAccessFile(filePath, "r");
			dfl = raf.length();
			String line = null;
			while ((line = raf.readLine()) != null) {
				line = line.trim();
				if (line.length() > 0) {
					int start = line.lastIndexOf('[');
					int end = line.lastIndexOf(']');
					line = line.substring(start + 1, end).trim();
					if (!wordMap.containsKey(line)) {
						wordMap.put(line, mapObject);
						wordList.add(line);
					}
				}
			}
			Log.info("WordProcessor initialized successfully.");;
		}catch(IOException e){
			throw new CCSystemException(e,Constants.SYSTEM_ERROR);
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
					//Eating exception as it has no meaning.
				}
			}
		}
		
		
		return this;
	}
	
	/*public WordProcessor init_() throws CCSystemException {
		String path = Constants.DICTIONARY_FILE_PATH;
		Log.info("Disctionary file path : " + path);
		if (path == null) {
			throw new CCSystemException(
					"Dictionary file path is not set. Look Constants.DICTIONARY_FILE_PATH variable.", Constants.SYSTEM_ERROR);
		}

		File dicFile = new File(path);
		if (!dicFile.exists()) {
			throw new CCSystemException(String.format(
					"Dictionary file %s does not exist.", path),Constants.SYSTEM_ERROR);
		}

		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(dicFile, "r");
			String line = null;
			while ((line = raf.readLine()) != null) {
				line = line.trim();
				if (line.length() > 0) {
					int start = line.lastIndexOf('[');
					int end = line.lastIndexOf(']');
					line = line.substring(start + 1, end).trim();
					//Log.debug(line);
					if (!wordMap.containsKey(line)) {
						wordMap.put(line, mapObject);
						wordList.add(line);
					}
				}
			}
			Log.info("WordProcessor initialized successfully.");;
		}catch(IOException e){
			throw new CCSystemException(e,Constants.SYSTEM_ERROR);
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
					//Eating exception as it has no meaning.
				}
			}
		}
		
		return this;
	}
*/
	public String getRandomWord() {
		
		int randomIndex = randomGen.nextInt(wordList.size());
		return wordList.get(randomIndex);
		
	}
	
	public String jumbledTheWord(String word){
		
		char c[] = word.toCharArray();
		Random r = new Random();
		int i = r.nextInt(c.length);
		int j = r.nextInt(c.length);
		
		int cursor = 0;
		for(;cursor+1<c.length;){
			char current = c[cursor];
			c[cursor] = c[cursor+1];
			c[cursor+1] = current;
			cursor+=2;
		}
		
		if(c.length>3){
			char a = c[i];
			c[i] = c[j];
			c[j] = a;
		}
		
		
		return new String(c);
	}
	
	
	boolean isaValidWord(String word){
		return wordMap.containsKey(word);
	}
	

	public static void main(String args[]) throws CCSystemException,
			IOException {
		WordProcessor wp = WordProcessor.getInstance().init();
		int i=10;
		while(i-->0){
		String word = wp.getRandomWord();
		Log.debug("Random word "+word);
		String jw = wp.jumbledTheWord(word);
		Log.debug("Jumbled word "+jw);
		}
	}

}
