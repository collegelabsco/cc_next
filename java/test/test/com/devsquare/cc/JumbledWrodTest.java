package test.com.devsquare.cc;

import org.junit.Assert;
import org.junit.Test;

import com.devsquare.cc.interfaces.Problem;
import com.devsquare.cc.log.Log;
import com.devsquare.cc.problem.jumbled.JumbledOutput;
import com.devsquare.cc.problem.jumbled.JumbledParameter;
import com.devsquare.cc.problem.jumbled.JumbledWordProblem;



public class JumbledWrodTest extends BaseTest{

	
	@Test
	public void testProblemOutput() throws Exception{
		
		Problem<JumbledOutput, JumbledParameter> p = new JumbledWordProblem();
		JumbledOutput o = p.get(null);
		Log.info(o.getOriginal()+" : "+o.getOutput());
		Assert.assertNotNull(o);
		Assert.assertEquals(o.getOutput().length(), o.getOriginal().length());
		
		
		
	}
	
}
