package test.com.devsquare.cc;

import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.devsquare.cc.CCSystemException;
import com.devsquare.cc.interfaces.Parameter;
import com.devsquare.cc.interfaces.Problem;
import com.devsquare.cc.log.Log;
import com.devsquare.cc.problem.jumbled.JumbledOutput;
import com.devsquare.cc.problem.jumbled.JumbledParameter;
import com.devsquare.cc.problem.jumbled.JumbledWordProblem;
import com.devsquare.cc.util.StringUtil;

public class JumbledWrodTest extends BaseTest {
	
	static Problem<JumbledOutput, JumbledParameter> p = null;
	
	@BeforeClass
	public static void setup() throws CCSystemException{
		Log.debug("Setting test setup");
		p = new JumbledWordProblem();
	}
	
	
	
	@AfterClass
	public static void tearDown(){
		Log.debug("Teardown test setup");
		p = null;
	}
	

	@Test
	public void testProblemOutput() throws Exception {
		
		JumbledOutput o = p.get(null);
		Log.info(o.getOriginal() + " : " + o.getOutput());
		Assert.assertNotNull(o);
		Assert.assertEquals(o.getOutput().length(), o.getOriginal().length());
		Assert.assertEquals(StringUtil.sortString(o.getOutput()),
				StringUtil.sortString(o.getOriginal()));

	}

	
	@Test
	public void testProblemValidate() throws CCSystemException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(Parameter.JUMBLED_WORD, "opt");
		params.put(Parameter.RESPONSE, new String[] { "pot", "top" });
		JumbledParameter jp = new JumbledParameter(params);
		JumbledOutput correctOutput = p.validate(jp);
		Assert.assertNull(correctOutput.getErrorOutput());

		params = new HashMap<String, Object>();
		params.put(Parameter.JUMBLED_WORD, "medo");
		params.put(Parameter.RESPONSE, new String[] { "pot", "assist" });
		jp = new JumbledParameter(params);
		correctOutput = p.validate(jp);
		Assert.assertNotNull(correctOutput.getErrorOutput());

	}

}
