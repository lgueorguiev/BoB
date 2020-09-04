package org.tlhg.bobAPI;

import java.sql.*;
import java.util.Properties;

public class DbRequest {
	private Connection connection;
	
	public DbRequest() throws ClassNotFoundException, SQLException {
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
	
	public ResultSet restRequest(String city, String[] tags) throws SQLException {
		try {
			Statement statement = connection.createStatement();
			
			String query = "";
			String filtered_add = "WITH filtered_add AS (SELECT * FROM addresses WHERE city = \'" + city + "\')";
			
			if (tags.length == 0) {
				query = filtered_add + "\nSELECT * FROM filtered_add JOIN businesses ON filtered_add.business_id = businesses.id;";
			}
			else {
				filtered_add += ",\n";
				String filtered_bus = "filtered_bus AS (SELECT * FROM businesses WHERE (tag = \'" + tags[0] + "\')";
				
				for(int i = 1; i < tags.length; i++) {
					filtered_bus += "OR (tag = \'" + tags[i] + "\')";
				}
				
				filtered_bus += ")";
				
				query = filtered_add + filtered_bus + "\nSELECT * FROM filtered_add JOIN filtered_bus ON filtered_add.business_id = filtered_bus.id;";
			}
			
			ResultSet results = statement.executeQuery(query);
			
			return results;
		}
		catch (SQLException e) {
			throw new SQLException("Encountered an error when executing given sql statement.", e);
		}
	}

}
