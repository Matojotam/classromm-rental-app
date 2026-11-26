package se.lexicon;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// DatabaseManager class implementing the UML diagram
public class DatabaseManager {
    // Private connection field as specified in the UML
    private Connection connection;

    /**
     * Establishes a connection to the MySQL database using credentials from db.properties.
     * @throws Exception if the connection fails
     */
    public void connect() throws SQLException {
        // Load properties from db.properties in the resources directory
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new SQLException("Cannot find db.properties file");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Extract database credentials
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        // Establish connection
        connection = DriverManager.getConnection(url, user, password);
    }

    /**
     * Executes a SQL query that returns a result set (e.g., SELECT statements).
     * @param sql The SQL query to execute
     * @return ResultSet containing the query results
     * @throws Exception if an error occurs during execution
     */
    public java.sql.ResultSet executeQuery(String sql) throws Exception {
        try (java.sql.Statement stmt = connection.createStatement()) {
            return stmt.executeQuery(sql);
        }
    }

    /**
     * Executes a SQL statement that modifies data (e.g., INSERT, UPDATE, DELETE).
     * @param sql The SQL statement to execute
     * @return The number of rows affected
     * @throws Exception if an error occurs during execution
     */
    public int executeUpdate(String sql) throws Exception {
        try (java.sql.Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }

    /**
     * Closes the database connection.
     * @throws Exception if closing the connection fails
     */
    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    /**
     * Returns the current database connection.
     * @return The Connection object
     */
    public Connection getConnection() {
        return connection;
    }
}