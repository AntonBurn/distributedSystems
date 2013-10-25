package ws13.dsys.net.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ws13.dsys.net.message.Message;

public class TCPSocketImp implements TCPSocket {
	
	static Logger tcpLog = LogManager.getLogger(TCPSocketImp.class.getName());

	private Socket cSocket;
	private InputStream in;
	private OutputStream out;
	
	public String connect(String host, int port){
		String sMsgReceived = "";
		
		if(cSocket != null && !cSocket.isClosed()){
			tcpLog.warn("Already connected");
			return "Already connected";
		}
		
		try {
			cSocket = new Socket(host, port);
			in = cSocket.getInputStream();
			out = cSocket.getOutputStream();
			
			sMsgReceived = receiveString();
		} catch (UnknownHostException uhe) {
			tcpLog.error("Unnable to find host: " + uhe.getMessage());
		} catch (IOException ioe) {
			tcpLog.error("Error while opening the streams: " + ioe.getMessage());
		}

		return sMsgReceived;
	}
	
	public void disconnect(){
		try {
			in.close();
			out.close();
			cSocket.close();
		} catch (IOException ioe) {
			tcpLog.error("Error while closing connection and streams: " + ioe.getMessage());
		}
	}
	
	public void sendString(String msg){
		byte byteMsg[] = Message.StringToByteMsg(msg);
		
		send(byteMsg);
	}
	
	public void send(byte byteMsg[]){
		if(cSocket.isConnected()){
			try {
				out.write(byteMsg);
				out.flush();
			} catch (IOException ioe) {
				tcpLog.error("Error while writing on the stream: " + ioe.getMessage());
			}
		}
	}
	
	public String receiveString(){
		return Message.ByteToStringMsg(receive());
	}
	
	public byte[] receive(){
		byte sByteMsgReceived[] = new byte[131072];
	
		try {
			in.read(sByteMsgReceived);
		} catch (IOException ioe) {
			tcpLog.error("Error while reading the stream: " + ioe.getMessage());
		}

		return sByteMsgReceived;
	}

	public boolean isConnected() {
		return cSocket == null ? false : cSocket.isConnected();
	}

}