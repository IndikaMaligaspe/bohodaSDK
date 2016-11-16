package rezg.rezos.bohoda.cllient.connectors;

public interface Connector {
	
	/**
	 * Author - Indika Maligaspe
	 * Date - 11/15/2016
	 * Description - The main interface that will be used by all connectors
	 */
	
	public final static int GET = 0;
	public final static int POST = 1;
	public final static int PUT = 2;
	public final static int DELETE = 3;
	

	

	public int connect(String host, String port, String file, int method, ConnectParams params, ConnectParams headers);
	public Object getServerOutput(Object objectIn);
	public BohodaErrors getConnectErrors();
}
