package week04.util;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * The ATM logger for the ATM project.
 * This class encapsulates a set of Java Logger instances
 * and setup the handlers.
 * 
 * @author Group 3
 */

public class AtmLogger {
	private static FileHandler fileTxt;
	private static SimpleFormatter formatterTxt;
	private static FileHandler fileHTML;
	private static Formatter formatterHTML;
	
	public static final String ATM_LOGGER = "ATM_LOGGER";
	static Logger atmLogger;

	/**
	 * Returns the singleton instance of the logger
	 * @return the singleton instance of the logger
	 */
	static Logger getATmLogger() {
		return atmLogger;
	}
	
	/**
	 * Initializes the logging system for our purposes
	 * @throws IOException
	 */
	public static void setup() throws IOException {
		// suppress the logging output to the console
		Logger rootLogger = Logger.getLogger("");
		Handler[] handlers = rootLogger.getHandlers();
		if (handlers.length > 0)	 {
			if (handlers[0] instanceof ConsoleHandler) {
				rootLogger.removeHandler(handlers[0]);
			}
		}
		
		//set the log level and the file names
		atmLogger.setLevel(Level.INFO);
		fileTxt = new FileHandler("Logging.txt");
		fileHTML = new FileHandler("Logging.html");
		
		//create a TXT formatter
		formatterTxt = new SimpleFormatter();
		fileTxt.setFormatter(formatterTxt);
		atmLogger.addHandler(fileTxt);
		
		//create a HTML formatter
		formatterHTML = new AtmHtmlLoggingFormatter();
		fileHTML.setFormatter(formatterHTML);
		atmLogger.addHandler(fileHTML);
	}
	
	/**
	 * static initializer
	 */
	static {
		//COnfigure the root application logger
		atmLogger = Logger.getLogger(ATM_LOGGER);
	}

	/**
	 * Adds the configured handlers to the provided logger
	 * Used by other classes that implement logging to
	 * ensure the logging is routed to the right files.
	 * 
	 * @param logger
	 */
	public static void addAtmHandler(Logger logger) {
		logger.addHandler(fileTxt);
		logger.addHandler(fileHTML);
	}
}