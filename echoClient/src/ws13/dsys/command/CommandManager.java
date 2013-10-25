package ws13.dsys.command;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ui.client.shell.Shell;
import ws13.dsys.net.socket.TCPSocketImp;

public class CommandManager {

	static Logger commandManagerLog = LogManager.getLogger(TCPSocketImp.class.getName());
	
	private TCPSocketImp tcpSocket;
	private Shell shell;
	
	public CommandManager(Shell shell){
		tcpSocket = new TCPSocketImp();
		this.shell = shell;
	}

	public void executeCommand(String command, String parameters){

		if(command.equals("connect")){
			connect(parameters);
		} else if (command.equals("disconnect")){
			disconnect();
		} else if (command.equals("send")){
			send(parameters);
		} else if (command.equals("loglevel")){
			changeLogLevel(parameters);
		} else if (command.equals("help")){
			help(parameters);
		} else if (command.equals("quit")){
			quit();
		}
	}

	private void connect(String parameters){
		String sMsgReceived = "";
		String params[];
		
		if(parameters == null){
			commandManagerLog.warn("No parameters were found");
			return;
		}

		params = parameters.trim().split("\\s+");

		if(params != null && params.length == 2){
			try{
				int port = Integer.parseInt(params[1]);
			
				sMsgReceived = tcpSocket.connect(params[0], port);
				
				shell.writeConsole(sMsgReceived.trim());

			} catch(NumberFormatException nfe){
				commandManagerLog.error("Port parameter should be a number: " + nfe.getMessage());
			}
		} else {
			commandManagerLog.warn("Incorrect number of parameters");
		}
	}
	
	private void disconnect(){
		if(tcpSocket != null && tcpSocket.isConnected()){
			tcpSocket.disconnect();
		} else {
			commandManagerLog.warn("Socket already disconnected");
		}
	}
	
	private void send(String parameters){
		String sMsgReceived = "";

		tcpSocket.sendString(parameters+System.getProperty("line.separator"));
		sMsgReceived = tcpSocket.receiveString();
		
		shell.writeConsole(sMsgReceived.trim());
	}
	
	private void changeLogLevel(String parameters){}
	
	private void help(String parameters){}
	
	private void quit(){
		disconnect();

		shell.setKeepReading(false);
	}
}
