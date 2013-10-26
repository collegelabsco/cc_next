package test.com.devsquare.cc;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.devsquare.cc.log.Log;


public class BaseTest {
	
	@BeforeClass
	public static void setup(){
		Log.debug("Setting test setup");
		
	}
	
	
	
	@AfterClass
	public static void tearDown(){
		Log.debug("Teardown test setup");
	}

}
