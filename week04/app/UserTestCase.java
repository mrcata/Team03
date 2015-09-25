package week04.app;

import test.AbstractTestCase;

public class UserTestCase extends AbstractTestCase
{
	/** Constructor */
	public UserTestCase()
	{
		super("UserTestCase");
	}

	/**
	 * Runs unit test
	 */
	@Override
	protected boolean runTest()
	{
		boolean result = false;
		try
		{			
			boolean test1 = testUserClassEquals();

			result = test1;
		}
		catch(Exception ex)
		{
			trace("TestUser: Unexpected error in runTest: " + ex.getMessage());
		}
		return result;
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
			trace("User.equals test passed");
		}
		else
		{
			trace("User.equals test failed");
		}
		
		return equalTest;
	}
}
