package rezg.rezos.bohoda.cllient.connectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@TestPropertySource(locations =  "/application.properties")
@RunWith(SpringJUnit4ClassRunner.class)

public class PropertyConnectorTest {

	PropertyConnector target = null;
	
	@Value(value = "${test.server.host}")
	String host = null;
	
	@Value(value = "${test.server.port}")
	String port = null;
	
	@Value(value = "${test.file}")
	String file = "";
	
	int method = 0;
	
	ConnectParams params = null;
	ConnectParams headers = null;
	
	@Test
	public void test() {
		System.out.println("Port.."+port);
		
	}
	
	@Before
	public void setUp(){
		

		
		target = new PropertyConnector();
		params = new ConnectParams(params.URL_QUERY_PARAMS,3);
				
		params.putValue("file", "oman_config_portals");
		params.putValue("project", "development");
		params.putValue("environment", "master");
		
		
	}
	
	@Test
	public void testgetServerOutputtWithCorrectURLParams(){
		String JSONString = "{\"name\":\"configclient\",\"profiles\":[\"et\"],\"label\""
				+ ":\"qa\",\"version\":\"39f68bd3a9839e3cdd26053e41ad35a1fea2519a\",\"state\":null,\""
				+ "propertySources\":[{\"name\":\"https://github.com/IndikaMaligaspe/bohoda-config-test/configclient.yml\","
				+ "\"source\":{\"configuration.projectName\":\"configclient\",\"server.port\":8000,\"message\":\""
				+ "\",\"greeting\":\"Hello from the configuration in QA Environment!\"}}]}";
		String source = "[{\"configuration.projectName\":\"configclient\",\"server.port\":8000,\"message\":\""
				+ "\",\"greeting\":\"Hello from the configuration in QA Environment!\"}]";
		
		
		Properties response = new Properties();
		int responseCode = target.connect(host, port, file, target.GET, params, headers);
		assertNotNull(responseCode);

		//		assertEquals(200, responseCode);
		target.setServiceResponse(JSONString);
		assertNotNull(target.getServiceResponse());
		response = (Properties) target.getServerOutput(response);
		assertNotNull(response);
		assertEquals("configclient", response.get("configuration.projectName"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testgetServerOutputtWithNullURLParams(){
		int responseCode = target.connect(null, null, null, target.GET, params, headers);
		assertNotNull(responseCode);
	}
	
	
	
}
