package test.com.devsquare.cc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

import com.devsquare.cc.problem.mapred.MapRedProblem;
import com.devsquare.cc.problem.mapred.MapredParameter;

public class MapredTest extends BaseTest {
	
	@Test
	public void randomDataGenTest() throws IOException{
		
		MapRedProblem mrp = new MapRedProblem();
		MapredParameter mp = new MapredParameter(null);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		mp.setOutputStream(baos);
		mrp.readFile(mp);
		System.out.println(baos.toString("UTF-8"));
		
	}
	

}
