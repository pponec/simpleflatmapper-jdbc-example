package net.ponec.jdbc.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.ujorm.tools.sql.SqlParamBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract public class AbstractDaoTest {

    protected Connection connection;

    /** Set up the database connection and initialize tables before each test. */
    @BeforeEach
    void setUp() throws SQLException {
        this.connection = getConnection();
    }

    /** Rollback the transaction and close the connection after each test.
     * The try-finally block ensures the connection is closed even if rollback fails. */
    @AfterEach
    void tearDown() throws SQLException {
        if (connection != null) {
            try {
                connection.rollback();
            } finally {
                connection.close();
            }
        }
    }

    protected SqlParamBuilder sqlBuilder() {
        return new SqlParamBuilder(connection);
    }

    /**
     * Establish a connection to the H2 in-memory database and create tables.
     * Auto-commit is disabled to allow for rolling back test data.
     *
     * @return The configured database connection
     * @throws SQLException If a database error occurs
     */
    private Connection getConnection() throws SQLException {
        var jdbcUrl = "jdbc:h2:mem:testdb";
        var databaseUser = "sa";
        var databasePassword = "";
        var result = DriverManager.getConnection(jdbcUrl, databaseUser, databasePassword);
        result.setAutoCommit(false);
        new CommonDao(result).initTables();
        return result;
    }
}