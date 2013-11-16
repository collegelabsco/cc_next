package test.com.devsquare.cc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.devsquare.cc.interfaces.Constants;
import com.devsquare.cc.interfaces.Parameter;
import com.devsquare.cc.log.Log;
import com.devsquare.cc.problem.bitmap.BitmapFileChannelReader;
import com.devsquare.cc.problem.bitmap.BitmapOutput;
import com.devsquare.cc.problem.bitmap.BitmapParameter;
import com.devsquare.cc.problem.bitmap.BitmapProblem;
import com.devsquare.cc.problem.bitmap.BitmapRequestLog;

public class BitmapTest extends BaseTest {

	@Test
	public void testBitmapFileReader() throws IOException {
		/*
		 * BitmapFileChannelReader reader = new BitmapFileChannelReader(
		 * "/Users/prabhat.kumar/git/cc_next/java/data/6.jpg");
		 */
		BitmapFileChannelReader reader = new BitmapFileChannelReader(new File(
				Constants.DATA_DIR + "/6.jpg"));
		int size = reader.size();
		Log.debug("file size" + size);
		String name = "4t.jpeg";
		String outputFile = "/Users/prabhat.kumar/git/cc_next/java/test/test_output/4t.jpeg";
		byte[] br = new byte[size];
		int sigments = 10;
		int piece = size / sigments;
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		while (--sigments > 0) {
			size = size - piece;
			Log.debug("Read Offset :" + size + " size : " + piece);
			map.put(size, piece);
		}
		Log.debug("Read Offset :" + 0 + " size : " + size);
		map.put(0, size);

		Set<Entry<Integer, Integer>> entrySet = map.entrySet();
		Iterator<Entry<Integer, Integer>> itr = entrySet.iterator();
		while (itr.hasNext()) {

			Entry<Integer, Integer> entry = itr.next();
			int offset = entry.getKey();
			int length = entry.getValue();
			reader.read(br, offset, length);
			Log.debug("write offset " + offset + " length " + length);
		}

		File f = new File(outputFile);
		f.delete();

		FileOutputStream fos = new FileOutputStream(f, false);
		fos.write(br);
		fos.flush();
		fos.close();

		// baos.write(b, off, len);
	}

	public void testBitmapProblem() throws Exception{
		
		BitmapProblem problem = BitmapProblem.get().init();
		BitmapOutput output = problem.getRandomFile();
		int size = (Integer)output.get(Parameter.FILE_SIZE);
		String fileId = (String)output.get(Parameter.FILE_ID);
		
		int sigments= 10;
		int piece = size/sigments;
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		while(--sigments>0){
			size=size-piece;
			Log.debug("Read Offset :"+size+" size : "+piece);
			map.put(size, piece);
		}
		Log.debug("Read Offset :"+0+" size : "+size);
		map.put(0,size);
		
		Set<Entry<Integer, Integer>> entrySet = map.entrySet();
		Iterator<Entry<Integer, Integer>> itr = entrySet.iterator();
		List<BitmapRequestLog> rLogList = new LinkedList<BitmapRequestLog>();
		while(itr.hasNext()){
			Entry<Integer, Integer> entry = itr.next();
			int position = entry.getKey();
			int length = entry.getValue();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(Parameter.FILE_ID, fileId);
			params.put(Parameter.LENGTH, length);
			params.put(Parameter.POSITION, position);
			BitmapOutput chunkOutput = problem.getFileChunk(new BitmapParameter(params));
			byte[] b = (byte[])chunkOutput.get(Parameter.BYTE);
			Assert.assertEquals(b.length, length);
			rLogList.add(new BitmapRequestLog(System.currentTimeMillis(), position, length, fileId));
		}
		
	}
}
