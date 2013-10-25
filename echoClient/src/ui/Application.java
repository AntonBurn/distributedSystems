package ui;

import java.io.IOException;

import ui.client.shell.Shell;

import org.apache.log4j.FileAppender;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.SimpleLayout;

public class Application {
	
	static Logger appLog = LogManager.getLogger(Application.class.getName());

	public static void main(String []args){

		BasicConfigurator.configure();

		try{
			SimpleLayout layout = new SimpleLayout();
			FileAppender appender = new FileAppender(layout, "logs/log", false);
			
			appLog.addAppender(appender);
		} catch(IOException ioe){
			appLog.error("Error while creating the log file: " + ioe.getMessage());
		}
		
		appLog.info("App Started");

		Shell shell = new Shell("EchoClient> ");

		shell.init();
		
		appLog.info("App Finished");
	}
}
