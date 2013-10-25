package ui.client.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ws13.dsys.command.CommandManager;

public class Shell {
	
	static Logger shellLog = LogManager.getLogger(Shell.class.getName());

	private boolean keepReading;
	private String shellMsg;
	private CommandManager commandManager;
	
	public Shell(String shellMsg){
		this.shellMsg = shellMsg;
		keepReading = true;
		commandManager = new CommandManager(this);
	}
	
	public void init(){
		String line;
		String command;
		String parameters;
		
		shellLog.info("Shell initialized");
		
		while(keepReading){
			System.out.print(shellMsg);

			line = readConsole();
			
			if(line != null && !line.trim().equals("")){
				
				if(line.trim().indexOf(" ") > -1){
					command = line.substring(0, line.trim().indexOf(" "));
					parameters = line.substring(line.trim().indexOf(" ") + 1);
				} else {
					command = line.trim();
					parameters = "";
				}

				commandManager.executeCommand(command.toLowerCase(), parameters);
			} else {
				shellLog.error("Invalid option");
			}
		}
		
		shellLog.info("Shell terminated");
	}
	
	public void terminate(){
		setKeepReading(false);
	}

	public String readConsole(){
		String line = "";
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in));
		
		try{

			line = br.readLine();

		} catch(IOException ioe){
			shellLog.error("Error while reading user's input: " + ioe.getMessage());
		}

		return line;		
	}
	
	public void writeConsole(String msg){
		System.out.println(shellMsg + msg);
	}

	public boolean isKeepReading() {
		return keepReading;
	}

	public void setKeepReading(boolean keepReading) {
		this.keepReading = keepReading;
	}

	public String getShellMsg() {
		return shellMsg;
	}

	public void setShellMsg(String shellMsg) {
		this.shellMsg = shellMsg;
	}
}
