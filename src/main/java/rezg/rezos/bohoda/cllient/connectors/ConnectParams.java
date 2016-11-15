package rezg.rezos.bohoda.cllient.connectors;

import java.util.HashMap;
import java.util.Map;

public class ConnectParams {

	public static final int DTO = 0;
	public static final int HEADERS = 1;
	public static final int URL_QUERY_PARAMS = 2;
	public static final int POST_PARAMS = 3;

	int paramType = 0;
	Map<String, Object> params = null;

	ConnectParams(int paramType,int size) {
		this.paramType = paramType;
		params = new HashMap(size);
	}

	public boolean putValue(String key, Object value){
		if((null==key) || (null == value)){
			return false;
		}
		
		if(params.containsKey(key))
			return false;
		params.put(key, value);
		
		return true;
	}
	
	public Object getValue(String key){
		return params.get(key);
	}
	
	public int getParamType() {
		return paramType;
	}

	public void setParamType(int paramType) {
		this.paramType = paramType;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	
}
