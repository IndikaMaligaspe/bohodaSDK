package rezg.rezos.bohoda.cllient.connectors;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ConnectParamsTest {

	ConnectParams target = null;
	
	@Test
	@Ignore
	public void test() {
		fail("Not yet implemented");
	}

	@Before
	public void setUp(){
		target = new ConnectParams(ConnectParams.URL_QUERY_PARAMS, 4);
	}
	
	@Test
	public void testPutValidValue(){
		boolean response = target.putValue("Test","Tes1");
		assertTrue(response);
	}
	
	@Test
	public void testPutDuplicateValues(){
		boolean response = target.putValue("Test","Tes1");
		assertTrue(response);
		response = target.putValue("Test","Tes1");
		assertFalse(response);
	}
	
	@Test
	public void testPutNullEntries(){
		boolean response = target.putValue("Test",null);
		assertFalse(response);
		response = target.putValue(null,null);
		assertFalse(response);
		response = target.putValue(null,"test");
		assertFalse(response);
	}
	
	
	@Test
	public void getPropValue(){
		boolean response = target.putValue("Test","Tes1");
		assertTrue(response);
		String responseObj = (String) target.getValue("Test");
		assertEquals("Tes1","Tes1");
	}
}
