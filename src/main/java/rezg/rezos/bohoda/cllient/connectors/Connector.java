package rezg.rezos.bohoda.cllient.connectors;

public interface Connector {

	public int connect(String host, String port, String file, int method, ConnectParams params, ConnectParams headers);
	public Object getServerOutput(Object objectIn);
	public BohodaErrors getConnectErrors();
}
