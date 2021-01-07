package dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

    /*
    Gets database information from file. Adds it to an array to be sent to all DAO classes
    so database connection could be easily possible
    */

    public class DBConnection {

        private static final String PROP_FILE = "data/connectionInfo.settings";
        private SQLServerDataSource ds;
        private Properties databaseProperties;

        public DBConnection() {
            ds = new SQLServerDataSource();
            databaseProperties = new Properties();

            try {
                databaseProperties.load(new FileInputStream(PROP_FILE));
                ds.setServerName(databaseProperties.getProperty("Server"));
                ds.setDatabaseName(databaseProperties.getProperty("Database"));
                ds.setUser(databaseProperties.getProperty("User"));
                ds.setPassword(databaseProperties.getProperty("Password"));
            } catch (IOException e) {
                //To DO
            }
        }

        /**
         * Returns Connection object which is able to
         * provide informations about database.
         *
         * @return The connection with database.
         * @throws SQLServerException if connection with database cannot be established.
         */
        public Connection getConnection() throws SQLServerException {
            return ds.getConnection();
        }
    }
