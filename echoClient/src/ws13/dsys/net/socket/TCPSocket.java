package ws13.dsys.net.socket;

public interface TCPSocket {
	
	public String connect(String host, int port);
	
	public void disconnect();
	
	public void send(byte byteMsg[]);
	
	public byte[] receive();

	public boolean isConnected();
}