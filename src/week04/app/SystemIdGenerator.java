package week04.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates a unique id across the system. Accounts for multiple instances across
 * the system.
 * @author Anish Patel
 * @version 1.0
 * @updated 24-Sep-2015
 */
public class SystemIdGenerator
{
	private static SystemIdGenerator m_singleton = null;
	
	/**
	 * This is a singleton factory method.
	 * The constructor is made private so the only way to get 
	 * a SystemIdGenerator is to use this method.
	 * 
	 * @return The single instance of SystemIdGenerator
	 */
	public synchronized static SystemIdGenerator getInstance()
	{
		if( m_singleton == null)
		{
			m_singleton = new SystemIdGenerator();
		}
		
		return m_singleton;
	}
	
	/**
	 * Default constructor
	 * This constructor is hidden and only the public factory is available getInstance()
	 */
	private SystemIdGenerator()
	{		
		m_idList = new ArrayList<Long>();
		m_nextId = 0L;
	}

	/**
	 * Generates a set of unique IDs within.
	 * 
	 * @param size    The number of unique IDs to generate
	 */
	public List<Long> generateIdSet(int size)
	{
		List<Long> set = new ArrayList<Long>();
		for(int i = 0; i < size; i++)
		{
			set.add(getNextId());
		}
		
		return set;
	}

	/**
	 * Generates a unique id across the system
	 */
	public long getNextId()
	{
		return nextId();
	}
	
	/**
	 * Returns the complete list of IDs, not including the next one
	 * 
	 * @return List of IDs
	 */
	public List<Long> getIdList()
	{
		return m_idList;
	}
	
	/** 
	 * Performs the id increment and 
	 * updates the internal list
	 * @return next ID.
	 */
	private synchronized Long nextId()
	{
		m_nextId++;
		m_idList.add(m_nextId);
		return m_nextId;
	}
	
	/**
	 * Next unique Id
	 */
	private long m_nextId;
	
	/**
	 * Contains allocated IDs
	 */
	private List<Long> m_idList;
}//end SystemIdGenerator