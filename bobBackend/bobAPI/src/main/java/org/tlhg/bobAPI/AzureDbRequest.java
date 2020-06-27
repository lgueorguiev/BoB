package org.tlhg.bobAPI;

import java.sql.*;
import java.util.Properties;

public class AzureDbRequest {
	private Connection connection;
	
	public AzureDbRequest() throws ClassNotFoundException, SQLException {
		// Initialize connection variables.
		String host = Config.getParam("HOST");
		String database = Config.getParam("DATABASE");
		String user = Config.getParam("USER");
		String password = Config.getParam("PASSWORD");
		
		// check that the driver is installed
		try
		{
			Class.forName("org.postgresql.Driver");
		}
		catch (ClassNotFoundException e)
		{
			throw new ClassNotFoundException("PostgreSQL JDBC driver NOT detected in library path.", e);
		}
		
		// Initialize connection object
		try
		{
			String url = String.format("jdbc:postgresql://%s/%s", host, database);
			
			// set up the connection properties
			Properties properties = new Properties();
			properties.setProperty("user", user);
			properties.setProperty("password", password);
			properties.setProperty("sslmode", "require");

			// get connection
			connection = DriverManager.getConnection(url, properties);
		}
		catch (SQLException e)
		{
			throw new SQLException("Failed to create connection to database.", e);
		}
	}
	
	public ResultSet restRequest(String t) throws SQLException {
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM test WHERE tag = \'" + t + "';";
			ResultSet results = statement.executeQuery(query);
			return results;
		}
		catch (SQLException e) {
			throw new SQLException("Encountered an error when executing given sql statement.", e);
		}
	}
	
	/**
	 * Works with a list of tags
	public ResultSet restRequest(String[] tags) throws SQLException {
		try {
			Statement statement = connection.createStatement();
			
			String query = "SELECT * FROM businesses WHERE (tag = " + tags[0] + ")";
			for(int i = 1; i < tags.length - 1; i++) {
				query += "OR (tag = " + tags[i] + ")";
			}
			
			query += ";";
			
			ResultSet results = statement.executeQuery(query);
			
			return results;
		}
		catch (SQLException e) {
			throw new SQLException("Encountered an error when executing given sql statement.", e);
		}
	}
	**/
}
