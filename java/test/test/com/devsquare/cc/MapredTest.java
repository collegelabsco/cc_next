package test.com.devsquare.cc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipOutputStream;

import org.junit.Assert;
import org.junit.Test;

import com.devsquare.cc.DataTransferMgr;
import com.devsquare.cc.interfaces.Callback;
import com.devsquare.cc.interfaces.Parameter;
import com.devsquare.cc.problem.mapred.MapRedOuput;
import com.devsquare.cc.problem.mapred.MapRedProblem;
import com.devsquare.cc.problem.mapred.MapredOriginalData;
import com.devsquare.cc.problem.mapred.MapredParameter;

public class MapredTest extends BaseTest {
	
/*	@Test
	public void randomDataGenTest() throws IOException{
		
		MapRedProblem mrp = new MapRedProblem();
		MapredParameter mp = new MapredParameter(null);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		mp.setOutputStream(baos);
		mrp.readFile(mp);
		System.out.println(baos.toString("UTF-8"));
		
	}
*/	
	@Test
	public void mapredTest() throws IOException{
		MapRedProblem mrp = new MapRedProblem();
		MapredParameter mp = new MapredParameter(new HashMap<String, Object>());
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		mp.setOutputStream(baos);
		mrp.readFile(mp);
		
		String content[] = baos.toString().split("\n");
		Map<Integer, Integer> ageCount = new HashMap<>();
		for(String line:content){
			String token[] = line.split("\\|");
			int count = 1;
			try{
			Integer age = Integer.valueOf(token[2]);
			
				if(ageCount.get(age) != null){
					count = ageCount.get(age)+1;
				}
				ageCount.put(age, count);
			}catch(NumberFormatException e){
				System.out.println("Line = "+line+" toekn "+token[2]);
				e.printStackTrace();
				throw e;
			}
		}
		
		MapredOriginalData mod = mp.getOriginal();
		Map<String, Object> params = new HashMap<>();
		params.put(Parameter.PEOPLE_AGE, ageCount);
		mp = new MapredParameter(params);
		mp.setMapredOrinalData(mod);
		MapRedOuput mro = mrp.validate(mp);
		Assert.assertNull(mro.getErrorOutput());
		
	}
	
	@Test
	public void zipTest() throws IOException{
		final MapRedProblem mrp = new MapRedProblem();
		final MapredParameter mp = new MapredParameter(new HashMap<String, Object>());
		final AtomicInteger count = new AtomicInteger();
		Callback cb = new Callback() {
			
			@Override
			public void process(ZipOutputStream zos) throws IOException {
				mp.setOutputStream(zos);
				mrp.readFile(mp);
				count.getAndIncrement();
			}
			
			@Override
			public boolean isFileAvailable() {
				return count.get() <5;
			}
			
			@Override
			public OutputStream getOutputStream() {
				return new ByteArrayOutputStream();
			}
		};
		
		
       DataTransferMgr dtm = new DataTransferMgr();
       dtm.prepareZipFromStream(cb);
	   
	}

}
