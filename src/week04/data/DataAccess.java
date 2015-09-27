package week04.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import week04.app.Account;
import week04.app.User;
import week04.util.AtmLogger;
/**
 * 
 * @author Anish
 *
 */
public class DataAccess {
	
	/** Logger reference */
	private static final Logger logger =
			Logger.getLogger(AtmLogger.ATM_LOGGER + "." + DataAccess.class.getName());

	/** DataAccess reference */
	private static DataAccess m_data;

	/** Formatted connection string. Update at initialization */
	private String m_connectionString;

	/** Used for the timestamp when updating datasource */
	private SimpleDateFormat m_formatter;

	/** Connection string */
	private String m_CONN_FMT = "jdbc:mysql://localhost:3306/atm?user=%s&password=%s";

	private Connection m_connect = null;
	
	// User templates
	
	private PreparedStatement m_selectUserStatement;
	private String INSERT_USER_SQL = "INSERT INTO atm.user (first_name, last_name, last_update) VALUES ('%s', '%s', '%s' )";
	private String SELECT_USER_SQL = "SELECT id, first_name, last_name from atm.user";
	private String LAST_INSERT_ID = "SELECT LAST_INSERT_ID();";
	private String DELETE_USER_BY_ID = "Delete from atm.user WHERE id=%d";
	private String SELECT_USER_SQL_FMT = "SELECT id, first_name, last_name from atm.user WHERE id=%d";
	private String UPDATE_USER_SQL = "UPDATE atm.user SET first_name='%s', last_name='%s', last_update='%s' WHERE id='%d'";

	// account templates

	private PreparedStatement m_selectAccountStatement;
	private String INSERT_ACCOUNT_SQL = "INSERT INTO atm.account (user_id,name,balance,last_update) values (%d,'%s', %f,'%s')";
	private String UPDATE_ACCOUNT_SQL = "UPDATE atm.account SET user_id=%d, name='%s',balance=%f,last_update='%s' WHERE id=%d";
	private String SELECT_ACCOUNT_SQL = "SELECT id, user_id, name, balance from atm.account WHERE id=%d";
	private String SELECT_ALL_ACCOUNTS_SQL = "SELECT id, user_id, name, balance from atm.account";
	private String DELETE_ACCOUNT_BY_ID_SQL = "DELETE from atm.account WHERE id=%d";

	/**
	 * Private parametrized constructor
	 * 
	 * @param user
	 *            DB username
	 * @param password
	 *            DB user password
	 * @throws AtmDataException
	 */
	private DataAccess(String user, String password) throws AtmDataException {
		m_formatter = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
		AtmLogger.addAtmHandler(logger);
		m_connectionString = String.format(m_CONN_FMT, user, password);

		connect();
		logger.info("Succesfully connected to database: " + m_connectionString);
	}

	/**
	 * Singleton pattern implementation
	 * 
	 * @return DataAccess instance
	 * @throws AtmDataException
	 */
	public static DataAccess getInstance() throws AtmDataException {
		// hand off to parameterized method
		return DataAccess.getInstance("root", "root");
	}

	/**
	 * Parameterized version to support testing and configuration
	 * 
	 * @param user
	 * @param password
	 * @return
	 * @throws AtmDataException
	 */
	public static DataAccess getInstance(String user, String password) throws AtmDataException {
		if (m_data == null) {
			m_data = new DataAccess(user, password);
		}
		return m_data;
	}

	/**
	 * Connect to the database
	 * 
	 * @throws AtmDataException
	 */
	public void connect() throws AtmDataException {
		try {
			// Load the MySql Driver
			Class.forName("com.mysql.jdbc.Driver");

			// setup the connection with the DB
			m_connect = DriverManager.getConnection(m_connectionString);
			m_selectUserStatement = m_connect.prepareStatement(SELECT_USER_SQL);
			m_selectAccountStatement = m_connect.prepareStatement(SELECT_ALL_ACCOUNTS_SQL);

		} catch (SQLException ex) {
			// Log exception
			System.out.println(ex.getMessage());
			throw new AtmDataException(ex);
		} catch (Exception ex) {
			// Log exception
			System.out.println(ex.getMessage());
			throw new AtmDataException(ex);
		}
	}

	/**
	 * Getter for the connection
	 */
	public Connection getConnection() {
		return m_connect;
	}

	/**
	 * Adds or updates an existing user. If the user is new, the returned user
	 * object has an updated system id and is fully valid for the follow on use
	 * 
	 * @param user
	 *            User to save
	 * @return Updated user reference
	 * @throws AtmDataException
	 */
	public User saveUser(User user) throws AtmDataException {
		User returnedUser = null;

		if (user.getUserId() == -1) {
			returnedUser = insertUser(user);
		} else {
			returnedUser = updateUser(user);
		}

		return returnedUser;
	}

	/**
	 * Update a user set of data
	 * 
	 * @param user
	 *            user to update
	 * @throws AtmDataException
	 */
	private User updateUser(User user) throws AtmDataException {
		Statement updateStmt = null;
		String updateTime = m_formatter.format(new java.util.Date());
		String sql = String.format(UPDATE_USER_SQL, user.getFirstName(), user.getLastName(), updateTime,
				user.getUserId());

		try {
			if (!m_connect.isClosed()) {
				updateStmt = m_connect.createStatement();
				updateStmt.execute(sql);
			}
		} catch (SQLException ex) {
			throw new AtmDataException("Error updating user - " + ex.getMessage(), ex);
		}

		return user;
	}

	/**
	 * Insert a new User
	 * 
	 * @param user
	 *            the User refernece to add
	 * @return updated User reference with new ID
	 */
	private User insertUser(User user) throws AtmDataException {
		User newUser = null;
		Statement insertStmt = null;
		Statement lastIndex = null;

		String updateTime = m_formatter.format(new java.util.Date());
		String sql = String.format(INSERT_USER_SQL, user.getFirstName(), user.getLastName(), updateTime);

		try {
			if (!m_connect.isClosed()) {
				insertStmt = m_connect.createStatement();
				insertStmt.execute(sql);

				// Successful insert
				lastIndex = m_connect.createStatement();

				// get the newly created ID
				ResultSet rs = lastIndex.executeQuery(LAST_INSERT_ID);
				while (rs.next()) {
					int id = rs.getInt(1);
					newUser = getUserById(id);
				}

			}
		} catch (SQLException ex) {
			throw new AtmDataException(ex);
		}

		return newUser;
	}

	/**
	 * Retrieves an existing user from the datastore
	 * 
	 * @param id
	 * @return User instance or null if not found
	 * @throws AtmDataException
	 */
	public User getUserById(long id) throws AtmDataException {
		User foundUser = null;
		Statement selectStmt = null;

		// prepare the sql
		String sql = String.format(SELECT_USER_SQL_FMT, id);

		try {
			Connection conn = getOpenConnection();
			selectStmt = conn.createStatement();
			ResultSet rs = selectStmt.executeQuery(sql);

			while (rs.next()) {
				foundUser = new User(rs.getInt(1), rs.getString(2), rs.getString(3));
			}

		} catch (SQLException ex) {
			throw new AtmDataException("Error retrieving user by ID - " + ex.getMessage(), ex);
		}
		return foundUser;
	}

	/**
	 * Retrieves the open Connection instance
	 * 
	 * @return the open connection instance
	 * @throws AtmDataException
	 *             if connection is not open or doesnt exist
	 */
	private Connection getOpenConnection() throws AtmDataException {
		try {
			if (m_connect == null || m_connect.isClosed()) {
				String msg = m_connect == null ? "Connection is invalid (null)" : "Connection is closed";
				throw new AtmDataException(msg);
			}
		} catch (SQLException e) {
			throw new AtmDataException("Error with isClosed() method");
		}

		return m_connect;
	}

	/**
	 * Returns the full set of users from the datastore.
	 * 
	 * @return
	 * @throws AtmDataException
	 */
	public List<User> getUsers() throws AtmDataException {
		List<User> userList = new ArrayList<User>();
		ResultSet resultSet = null;

		try {
			resultSet = m_selectUserStatement.executeQuery();

			while (resultSet.next()) {
				long userId = resultSet.getLong("id");
				String first = resultSet.getString("first_name");
				String last = resultSet.getString("last_name");

				userList.add(new User(userId, first, last));
			}
		} catch (SQLException ex) {
			// log error
			throw new AtmDataException(ex);
		}

		return userList;
	}

	/**
	 * Closes the data access resources
	 */
	public void close() {
		try {
			Connection m_connect = getConnection();
			m_connect.close();
		} catch (SQLException e) {
			// eat any exception at this phase
		}
	}

	public User removeUser(User user) throws AtmDataException {
		User userExists = getUserById(user.getUserId());
		User deletedUser = null;
		Statement deleteStatement = null;

		if (userExists != null) {
			String sql = String.format(DELETE_USER_BY_ID, userExists.getUserId());

			try {
				if (!m_connect.isClosed()) {
					deleteStatement = m_connect.createStatement();
					if (deleteStatement.execute(sql)) {
						deletedUser = userExists;
					}
				}
			} catch (SQLException e) {
				throw new AtmDataException("Error removing user;", e);
			}
		}

		return deletedUser;
	}

	public List<Account> getAccounts() throws AtmDataException {
		List<Account> accountList = new ArrayList<Account>();
		ResultSet resultSet = null;

		try {
			resultSet = m_selectAccountStatement.executeQuery();

			while (resultSet.next()) {
				long id = resultSet.getLong("id");
				User user = getUserById(resultSet.getLong("user_id"));
				String name = resultSet.getString("name");
				double balance = resultSet.getDouble("balance");
				
				accountList.add(new Account(id, user, name, balance));
			}
		} catch (SQLException ex) {
			// log error
			throw new AtmDataException(ex);
		}

		return accountList;
	}
	
	public Account getAccountByID(long id) throws AtmDataException{
		Account foundAccount = null;
		Statement selectStmt = null;

		// prepare the sql
		String sql = String.format(SELECT_ACCOUNT_SQL, id);

		try {
			Connection conn = getOpenConnection();
			selectStmt = conn.createStatement();
			ResultSet rs = selectStmt.executeQuery(sql);

			while (rs.next()) {
				foundAccount = new Account(rs.getInt(1), getUserById(rs.getInt(2)), rs.getString(3), rs.getDouble(4));
			}

		} catch (SQLException ex) {
			throw new AtmDataException("Error retrieving Account by ID - " + ex.getMessage(), ex);
		}
		return foundAccount;
	}

	public Account saveAccount(Account account) throws AtmDataException {
		Account returnedAccount = null;

		if (account.getAccountId() == -1) {
			returnedAccount = insertAccount(account);
		} else {
			returnedAccount = updateAccount(account);
		}

		return returnedAccount;
	}

	/**
	 * Update a account set of data
	 * 
	 * @param account
	 *            account to update
	 * @throws AtmDataException
	 */
	private Account updateAccount(Account account) throws AtmDataException {
		Statement updateStmt = null;
		String updateTime = m_formatter.format(new java.util.Date());
		String sql = String.format(UPDATE_ACCOUNT_SQL, account.getUser().getUserId(), account.getName(), account.getBalance(), updateTime,
				account.getAccountId());

		try {
			if (!m_connect.isClosed()) {
				updateStmt = m_connect.createStatement();
				updateStmt.execute(sql);
			}
		} catch (SQLException ex) {
			throw new AtmDataException("Error updating account - " + ex.getMessage(), ex);
		}

		return account;
	}

	/**
	 * Insert a new Account
	 * 
	 * @param account
	 *            the Account reference to add
	 * @return updated Account reference with new ID
	 */
	private Account insertAccount(Account account) throws AtmDataException {
		Account newAccount = null;
		Statement insertStmt = null;
		Statement lastIndex = null;

		String updateTime = m_formatter.format(new java.util.Date());
		String sql = String.format(INSERT_ACCOUNT_SQL, account.getUser().getUserId(),account.getName(), account.getBalance(),updateTime);

		try {
			if (!m_connect.isClosed()) {
				insertStmt = m_connect.createStatement();
				insertStmt.execute(sql);

				// Successful insert
				lastIndex = m_connect.createStatement();

				// get the newly created ID
				ResultSet rs = lastIndex.executeQuery(LAST_INSERT_ID);
				while (rs.next()) {
					int id = rs.getInt(1);
					newAccount = getAccountByID(id);
				}

			}
		} catch (SQLException ex) {
			throw new AtmDataException(ex);
		}

		return newAccount;
	}

	public Account removeAccount(Account account) throws AtmDataException {
		Account accountExists = getAccountByID(account.getAccountId());
		Account deletedAccount = null;
		Statement deleteStatement = null;

		if (accountExists != null) {
			String sql = String.format(DELETE_ACCOUNT_BY_ID_SQL, accountExists.getAccountId());

			try {
				if (!m_connect.isClosed()) {
					deleteStatement = m_connect.createStatement();
					if (deleteStatement.execute(sql)) {
						deletedAccount = accountExists;
					}
				}
			} catch (SQLException e) {
				throw new AtmDataException("Error removing account;", e);
			}
		}

		return deletedAccount;
	}

}
