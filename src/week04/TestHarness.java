package week04;

import java.io.IOException;

import test.TestEngine;
import week04.app.AccountTestCase;
import week04.app.UserTestCase;
import week04.data.DataAccessTestCase;
import week04.util.AtmLogger;

/**
 * File: TestHarness.java
 */
class TestHarness
{
    public static void main(String[] args)
    {
    	trace("Starting test...");

		trace(" -- setup test data");
		try
		{
			AtmLogger.setup();
		}
		catch(IOException e)
		{
			trace(" -- Error setting up logger");
			e.printStackTrace();
		}
		
    	TestEngine engine = new TestEngine();
    	engine.addTest(new UserTestCase());
    	engine.addTest(new AccountTestCase());
    	engine.addTest(new DataAccessTestCase());
    	engine.addTest(new TestLogging());

    	engine.runTests();

    	trace("Completed test");
    }

	static private void trace(String msg)
	{
		System.out.println(msg);
	}
}