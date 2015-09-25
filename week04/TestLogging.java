package week04;

import test.AbstractTestCase;
import java.util.logging.Level;
import java.util.logging.Logger;
import week04.util.AtmLogger;

/**
 * Tests the logging subsystem
 * @author scottl
 *
 */
public class TestLogging extends AbstractTestCase
{
	// use the classname for the logger, this way you can refactor
	private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());
//	private final static Logger testLogger = Logger.getLogger(TestLogging.class.getName());
	private final static Logger testLogger = 
			Logger.getLogger(AtmLogger.ATM_LOGGER + "." + TestLogging.class.getName());

	/**
	 * Protected constructor
	 */
	protected TestLogging()
	{
		super("TestLogging");
	}

	@Override
	protected boolean runTest()
	{
		return testLogger();
	}

	private boolean testLogger()
	{
		boolean result = true;
		try
		{
			AtmLogger.setup();
			AtmLogger.addAtmHandler(LOGGER);	// Logger class
			AtmLogger.addAtmHandler(testLogger);// This class

			trace("AtmLogger Instance");
			
			// set the LogLevel to Severe, only severe Messages will be written
			LOGGER.setLevel(Level.SEVERE);
			LOGGER.severe("SEVERE Level: Severe Log");
			LOGGER.warning("SEVERE Level: Warning Log");
			LOGGER.info("SEVERE Level: Info Log");
			LOGGER.finest("Really not important");

			// set the LogLevel to Info, severe, warning and info will be
			// written
			// finest is still not written
			LOGGER.setLevel(Level.INFO);
			LOGGER.severe("INFO Level: Severe Log");
			LOGGER.warning("INFO Level: Warning Log");
			LOGGER.info("INFO Level: Info Log");
			LOGGER.finest("Really not important");

			// set the LogLevel to Severe, only severe Messages will be written
			LOGGER.setLevel(Level.FINE);
			LOGGER.severe("FINE Level: Severe Log");
			LOGGER.warning("FINE Level: Warning Log");
			LOGGER.info("FINE Level: Info Log");
			LOGGER.finest("FINE Level: Really not important");
			
			trace("TestLogger Instance");
			
			// set the LogLevel to Severe, only severe Messages will be written
			testLogger.setLevel(Level.SEVERE);
			testLogger.severe("SEVERE Level: Severe Log");
			testLogger.warning("SEVERE Level: Warning Log");
			testLogger.info("SEVERE Level: Info Log");
			testLogger.finest("Really not important");

			// set the LogLevel to Info, severe, warning and info will be
			// written
			// finest is still not written
			testLogger.setLevel(Level.INFO);
			testLogger.severe("INFO Level: Severe Log");
			testLogger.warning("INFO Level: Warning Log");
			testLogger.info("INFO Level: Info Log");
			testLogger.finest("Really not important");

			// set the LogLevel to Severe, only severe Messages will be written
			testLogger.setLevel(Level.FINE);
			testLogger.severe("FINE Level: Severe Log");
			testLogger.warning("FINE Level: Warning Log");
			testLogger.info("FINE Level: Info Log");
			testLogger.finest("FINE Level: Really not important");
			
		}
		catch(Exception ex)
		{
			result = false;
			this.addResultMessage("Error testing logger");
			this.addResultMessage(ex.getMessage());
		}
		
		return result;
	}
}
