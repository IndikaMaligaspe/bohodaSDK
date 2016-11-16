package rezg.rezos.bohoda.cllient.connectors;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;

public class PropertyConnector implements Connector {
	
	/**
	 * Author - Indika Maligaspe
	 * Date - 11/15/2016
	 * Description - Connector to get Config Server which will get the Property Object String format 
	 * and convert to a Java.util.Properties object.
	 * This will be mainly used by Legacy applications which does not have the direct connectivity to ConfigServer.
	 */
	
	private Logger logger = (Logger) Logger.getInstance(PropertyConnector.class);
	private URL mURL;
	private HttpURLConnection uConn;
	private DataInputStream dis;
	private String serviceResponse;
	private String bohodaService;
	private String HTTP_METHOD = null;
	private DataOutputStream dout;

	@Override
	public int connect(String host, String port, String file, int method, ConnectParams params, ConnectParams headers) {

		if(null==host)
			throw new IllegalArgumentException("Invalid Host..");
		if(null==port)
			throw new IllegalArgumentException("Invalid port..");
		if(null==file)
			throw new IllegalArgumentException("Invalid file..");

		
		int responseCode = 500;
		try {
			// Set HttpMethod
			setHttpMethod(method);
			bohodaService = host + ":" + port + file;
			logger.debug(bohodaService);
			mURL = new URL(bohodaService);
			uConn = (HttpURLConnection) mURL.openConnection();
			uConn.setDoInput(true);
			uConn.setDoOutput(true);
			uConn.setRequestMethod(HTTP_METHOD);
			responseCode = uConn.getResponseCode();

			dis = new DataInputStream(uConn.getInputStream());
			dout = new DataOutputStream(uConn.getOutputStream());

			// Set the headers if headers are available
			if (null != headers) {
				HashSet<String> keys = (HashSet<String>) headers.getKeys();
				Iterator<String> iter = keys.iterator();
				while (iter.hasNext()) {
					String key = (String) iter.next();
					String value = headers.getValue(key).toString();
					uConn.setRequestProperty(key, value);
				}
			}

			// Write output data if this is not a GET request
			if (method != GET) {
				HashSet<String> keys = (HashSet<String>) params.getKeys();
				Iterator<String> iter = keys.iterator();
				while (iter.hasNext()) {
					String key = (String) iter.next();
					String value = params.getValue(key).toString();
					dout.writeBytes(key + "=" + value);
				}
			}

			// Read server output
			StringBuffer sbr = new StringBuffer();
			String line = dis.readLine();
			sbr.append(line);
			while (null != (line = dis.readLine())) {
				line = dis.readLine();
				sbr.append(line);
			}
			this.serviceResponse = sbr.toString();
		} catch (Exception e) {
			e.printStackTrace();
			responseCode = 500;
		}

		return responseCode;
	}

	private void setHttpMethod(int method) {
		switch (method) {
		case GET:
			HTTP_METHOD = "GET";
			break;
		case POST:
			HTTP_METHOD = "POST";
			break;
		case PUT:
			HTTP_METHOD = "PUT";
			break;
		case DELETE:
			HTTP_METHOD = "DELETE";
			break;
		default:
			HTTP_METHOD = "GET";
			break;
		}
	}

	@Override
	public Object getServerOutput(Object objectIn) {
		Properties prop = (Properties)objectIn;
		Map<String, String> propertyMap = new HashMap<String, String>();

		JSONArray array = JsonPath.parse(serviceResponse).read("$.propertySources..source");

		if (array.isEmpty())
			throw new IllegalArgumentException("Source List missing from property file!!!");
		if (array.size() == 0)
			throw new IllegalArgumentException("Source List size is 0 for property file!!!");
		if ((array.size() == 1) && (null == array.get(0)))
			throw new IllegalArgumentException("Source is NULL !!!");

		String propertyString = new String(array.toJSONString());
		logger.debug("Converted JSON String *************** " + propertyString);
		propertyString = propertyString.substring(1, propertyString.length() - 1);
		try {
			propertyMap = new ObjectMapper().readValue(propertyString,
					new com.fasterxml.jackson.core.type.TypeReference<Map<String, String>>() {
					});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prop.putAll(propertyMap);
		propertyMap.clear();
		return prop;
	}

	@Override
	public BohodaErrors getConnectErrors() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getServiceResponse() {
		return serviceResponse;
	}

	public void setServiceResponse(String serviceResponse) {
		this.serviceResponse = serviceResponse;
	}
	
	

}
