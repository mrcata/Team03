package week04.app;

import java.util.logging.Logger;

import week04.TestLogging;
import week04.util.AtmLogger;

/**
 * Represents a user in the system
 * 
 * @author Scott LaChance
 *
 */
public class User
{
	/**
	 * Default constructor
	 */
	public User()
	{
		this(-1, "Default", "Default");
	}
	
	/**
	 * New user constructor.
	 * Used when creating a new user from scratch.
	 * ID defaults to -1
	 * @param first User first name
	 * @param last User last name
	 */
	public User(String first, String last)
	{
		this(-1, first, last);
	}
	
	/**
	 * Parameterized constructor
	 * 
	 * @param id userid A unique System ID instance
	 * @param first First name
	 * @param last last name
	 */
	public User(long id, String first, String last)
	{
		m_userId = id;
		m_firstName = first;
		m_lastName = last;
	}
	
	/**
	 * @return the m_userId
	 */
	public long getUserId()
	{
		return m_userId;
	}
	/**
	 * @param m_userId the m_userId to set
	 */
	public void setUserId(long m_userId)
	{
		this.m_userId = m_userId;
	}
	
	/**
	 * @return the m_firstName
	 */
	public String getFirstName()
	{
		return m_firstName;
	}

	/**
	 * @param m_firstName the m_firstName to set
	 */
	public void setFirstName(String m_firstName)
	{
		this.m_firstName = m_firstName;
	}
	/**
	 * @return the m_lastName
	 */
	public String getLastName()
	{
		return m_lastName;
	}
	/**
	 * @param m_lastName the m_lastName to set
	 */
	public void setLastName(String m_lastName)
	{
		this.m_lastName = m_lastName;
	}
	
	/**
	 * Compares the contents of a User instance
	 * 
	 * @return true if the id, first and last names are equal
	 */
	@Override
	public boolean equals(Object obj)
	{
		boolean result = true;
		
		if( obj == null )
		{
			result = false;
		}
		else if( getClass() != obj.getClass())
		{
			result = false;
		}
		else
		{
			// valid User object, check the contents
			final User otherUser = (User) obj;
			if( !this.m_firstName.equals(otherUser.m_firstName) ||
				!this.m_lastName.equals(otherUser.m_lastName) ||
				!(this.m_userId == otherUser.m_userId))
			{
				result = false;
			}
		}
				
		return result;
	}
	
	/**
	 * Overrides toString to provide a formatted representation of the User instance
	 */
	@Override
	public String toString()
	{
		String fmt = String.format("ID: %d, %s %s", this.m_userId, this.m_firstName, this.m_lastName);
		return fmt;
	}
	
	
	private String m_firstName;
	private String m_lastName;
	private long m_userId;
}
