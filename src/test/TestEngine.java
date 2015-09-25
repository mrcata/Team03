package test;

import java.util.ArrayList;

/**
 * Test engine
 * Executes registered tests and keeps track of individual
 * results.
 */
public class TestEngine
{
	private enum STATUS {PASSED, FAILED};
	/**
	 * Default constructor
	 */
	public TestEngine()
	{
		m_tests = new ArrayList<AbstractTestCase>();
	}
	
	/**
	 * Add a test case for the engine to run
	 * 
	 * @param test An AbstractTestCase subclass instance
	 */
	public void addTest(AbstractTestCase test)
	{
		m_tests.add(test);
	}
	
	/**
	 * Iterates the list of tests and executes them
	 */
	public void runTests()
	{
		STATUS status = STATUS.PASSED;		// overall test harness status 
		STATUS testStatus = STATUS.PASSED;	// individual test status
		
		for(AbstractTestCase test : m_tests)
		{		
			// reset individual test status
			testStatus = STATUS.PASSED;
			
			trace(" -- starting test: " + test.getName());
			
			// This is using the polymorphic behavior of OOP. 
			// Each test case implements the abstract runTest method
			// which is uniquely implemented by each subclass test case
			boolean result = test.run();
			if( !result  )
			{
				displayResult(" --", test.getName(), STATUS.FAILED, test.results());
				status = STATUS.FAILED; // overall test harness status
				testStatus = STATUS.FAILED; // individual test status
			}

			displayResult(" -- Completed", test.getName(), testStatus, test.results());
		}
		
		displayResult("--", "Test Harness", status, "");
	}
    
	static private void displayResult(String pad, String what, STATUS status, String results)
	{
		String msg = String.format("%s %s: **%s** - %s", pad, what, status, results);
		trace(msg);
	}
	
	static private void trace(String msg)
	{
		System.out.println(msg);
	}   
    
    private ArrayList<AbstractTestCase> m_tests;
}