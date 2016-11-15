package rezg.rezos.bohoda.cllient.connectors;

public class BohodaErrors extends Exception {

	/**
	 * Author - Indika Maligaspe
	 * Date - 11/15/2016
	 * Description - This class is used to capture any errors that could 
	 * get thrown out while trying to Connect to Bohoda server and retrieve properties.
	 */
	
	private static final long serialVersionUID = 1L;
	
	static final int CONNECTION_ERROR = 0;
	static final int DATA_RETRIEVAL_ERROR=1;
	static final int ERROR_IN_PIPE = 2;
	
	String errorMessage;
	String messageDescription;
	int errorType;
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getMessageDescription() {
		return messageDescription;
	}
	public void setMessageDescription(String messageDescription) {
		this.messageDescription = messageDescription;
	}
	public int getErrorType() {
		return errorType;
	}
	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public static int getConnectionError() {
		return CONNECTION_ERROR;
	}
	
	
}
