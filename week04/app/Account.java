package week04.app;

/**
 * An account in the bank system.
 * 
 * @author Scott
 *
 */
public class Account
{
	/**
	 * Default constructor
	 */
	public Account()
	{
		this(-1, new User(), "Default Account", 0.0);
	}
	
	/**
	 * Parameterized constructors
	 * @param id account id
	 * @param user User reference
	 * @param name account name
	 * @param balanace Account balance
	 */
	public Account(long id, User user, String name, double balance)
	{
		m_accountId = id;
		m_user = user;
		m_accountName = name;
		m_balance = balance;
	}
	
	/**
	 * Parameterized constructors
	 * 
	 * Used for the application to create new Accounts
	 * 
	 * @param user User reference
	 * @param name account name
	 * @param balanace Account balance
	 */	
	public Account(User user, String name, double balance)
	{
		this(-1, user, name, balance);
	}
	
	/**
	 * @return the Account ID
	 */
	public long getAccountId()
	{
		return m_accountId;
	}
	
	/** 
	 * Set the account id
	 * @param id
	 */
	public void setAccountId(long id)
	{
		m_accountId = id;
	}
	
	/**
	 * Get the user reference
	 * @return
	 */
	public User getUser()
	{
		return m_user;
	}	
	
	/**
	 * 
	 * @param user Assign the user to the account
	 */
	public void setUser(User user)
	{
		this.m_user = user;
	}
		
	/**
	 * @return the account name
	 */
	public String getName()
	{
		return m_accountName;
	}
	
	/**
	 * Return account balance
	 * @return Current account balance
	 */
	public double getBalance()
	{
		return m_balance;
	}
	
	/**
	 * Set the balance for this account
	 * 
	 * @param balance value to assign
	 */
	public void setBalance(double balance)
	{
		m_balance = balance;
	}

	/**
	 * @param name New account name to set
	 */
	public void setName(String name)
	{
		this.m_accountName = name;
	}

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
			final Account otherUser = (Account) obj;
			if( !this.m_accountName.equals(otherUser.m_accountName) ||
				!this.m_user.equals(otherUser.m_user) ||
				!(this.m_balance == otherUser.m_balance) ||
				!(this.m_accountId == otherUser.m_accountId))
			{
				result = false;
			}
		}
				
		return result;
	}
	
	@Override
	public String toString()
	{
		String fmt = String.format("ID: %d, %s %f", this.m_accountId, this.m_accountName, this.m_balance);
		return fmt;
	}
	
	private long m_accountId;
	private User m_user;
	private String m_accountName;
	private double m_balance;


}
