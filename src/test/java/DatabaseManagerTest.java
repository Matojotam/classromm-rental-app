

import org.junit.jupiter.api.*;
import se.lexicon.DatabaseManager;

import java.sql.*;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for the DatabaseManager class.
 * This test verifies that the DatabaseManager can connect to the database,
 * execute queries, and close the connection properly.
 */
public class DatabaseManagerTest {

    private static DatabaseManager dbManager;

    /**
     * Sets up the test environment before all tests.
     */
    @BeforeAll
    public static void setUp() {
        try {
            dbManager = new DatabaseManager();
        } catch (Exception e) {
            fail("Failed to initialize DatabaseManager: " + e.getMessage());
        }
    }

    /**
     * Cleans up the test environment after all tests.
     */
    @AfterAll
    public static void tearDown() {
        try {
            dbManager.close();
        } catch (Exception e) {
            fail("Failed to close database connection: " + e.getMessage());
        }
    }

    /**
     * Test that the `connect()` method doesn't throw an exception
     * when the `db.properties` file exists.
     */
    @Test
    public void testConnectSuccess() {
        try {
            dbManager.connect();
            assertNotNull(dbManager.getConnection(), "Connection should not be null");
        } catch (Exception e) {
            fail("connect() should not throw an exception: " + e.getMessage());
        }
    }

    /**
     * Test that the `connect()` method throws an exception
     * when the `db.properties` file is missing.
     */
    @Test
    public void testConnectFailure() {
        try {
            // Temporarily remove the db.properties file (simulate missing file)
            // This test assumes the file is present by default
            // In a real CI/CD pipeline, you'd mock this behavior
            dbManager.connect();
            fail("connect() should throw an exception when db.properties is missing");
        } catch (Exception e) {
            // Expected exception
            assertTrue(e.getMessage().contains("Cannot find db.properties file"));
        }
    }

    /**
     * Test that the `executeQuery()` method can return a result set.
     */
    @Test
    public void testExecuteQuery() {
        try {
            String sql = "SELECT 1 AS test_column";
            ResultSet rs = dbManager.executeQuery(sql);
            assertNotNull(rs, "executeQuery() should return a ResultSet");
            assertTrue(rs.next(), "ResultSet should have at least one row");
            assertEquals("1", rs.getString("test_column"), "ResultSet should return expected value");
        } catch (Exception e) {
            fail("executeQuery() should not throw an exception: " + e.getMessage());
        }
    }

    /**
     * Test that the `executeUpdate()` method can execute an update.
     */
    @Test
    public void testExecuteUpdate() {
        try {
            String sql = "UPDATE Classroom SET isAccessible = TRUE WHERE room_id = 1";
            int rowsAffected = dbManager.executeUpdate(sql);
            assertTrue(rowsAffected > 0, "executeUpdate() should affect at least one row");
        } catch (Exception e) {
            fail("executeUpdate() should not throw an exception: " + e.getMessage());
        }
    }

    /**
     * Test that the `close()` method properly closes the connection.
     */
    @Test
    public void testClose() {
        try {
            dbManager.close();
            assertNull(dbManager.getConnection(), "Connection should be closed and null");
        } catch (Exception e) {
            fail("close() should not throw an exception: " + e.getMessage());
        }
    }
}
