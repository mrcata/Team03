package week04;

/**
 * ATM Application Exception class 
 * @author scottl
 *
 */
public class AtmException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public AtmException()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * Extends the base Exception class constructor
	 * @param arg0
	 */
	public AtmException(String arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Extends the base Exception class constructor
	 * @param arg0
	 */
	public AtmException(Throwable arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Extends the base Exception class constructor
	 * @param arg0
	 */
	public AtmException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Extends the base Exception class constructor
	 * @param arg0
	 */
	public AtmException(String arg0, Throwable arg1, boolean arg2, boolean arg3)
	{
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

}
