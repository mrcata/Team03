package week04;


import java.util.List;

import test.AbstractTestCase;
import week04.app.User;
import week04.app.UserManager;
import week04.util.AtmLogger;

public class TestApp extends AbstractTestCase
{
	/** Constructor */
	public TestApp()
	{
		super("TestApp");
	}

	/**
	 * Executes multiple threads to test ID generator thread safety. Measures
	 * execution time
	 */
	@Override
	protected boolean runTest()
	{
		boolean result = false;
		try
		{
			AtmLogger.setup();
			trace("testUserClassEquals");
			boolean classTest = testUserClassEquals();	
			trace("testUserManager");
			boolean userMgrTest = testUserManager();
			
			result = classTest && userMgrTest;
		}
		catch(Exception ex)
		{
			addResultMessage("Unexpected error in runTest: " + ex.getMessage());
		}
		return result;
	}
	
	private boolean testUserManager()
	{
		boolean success = true;
		
		UserManager mgr = new UserManager();
		
		trace(" Testing invalid user");
		// Invalid user
		User user = new User();
		
		try
		{
			mgr.addUser(user);
			addResultMessage("Failed to detect invalid User reference");
			success = false;
		}
		catch(AtmException ex)
		{
			// success			
		}
		
		trace(" Testing valid user");
		long newId = week04.app.SystemIdGenerator.getInstance().getNextId();
		user.setUserId(newId);
		
		try
		{
			mgr.addUser(user);	
			List<User> list = mgr.getUserList();
			if( list.size() == 0 )
			{
				success = false;
				addResultMessage("Failed to add user");
			}
			
		}
		catch(AtmException ex)
		{
			// failed		
			success = false;	
			addResultMessage("Failed to add valid User reference");
		}
		
		return success;
	}

	private boolean testUserClassEquals()
	{
		trace("Testing User class and equals");
		boolean equalTest = true;
		
		// simple user creation
		User user = new User();						
		trace(user.toString());
		
		User user2 = new User();						
		trace(user2.toString());
		
		// expect to be same
		if( !user.equals(user2))
		{
			equalTest = false;
		}
		
		user2.setFirstName("Jim");
		user2.setLastName("Bo");
		user2.setUserId(1);					
		trace(user2.toString());

		// expect to be different
		if( user.equals(user2))
		{
			equalTest = false;
		}
		
		User user3 = new User(user2.getUserId(), user2.getFirstName(), user2.getLastName());		
		trace(user3.toString());
		
		// expect to be same
		if( !user3.equals(user2))
		{
			equalTest = false;
		}
		
		if(equalTest)
		{
			addResultMessage("User.equals test passed");
		}
		else
		{
			addResultMessage("User.equals test failed");
		}
		
		return equalTest;
	}
}
	
