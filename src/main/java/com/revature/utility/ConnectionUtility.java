package com.revature.utility;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtility {
//TODO 2: create the connection class and test it
    public static Connection getConnection() throws SQLException {

        String url = System.getenv("db_url");
        String password = System.getenv("db_password");
        String username = System.getenv("db_username");

//TODO 3:  using the postgres driver on the DriverManager
        DriverManager.registerDriver(new Driver());
//TODO 4: get the connection Object using DriverManager
        Connection connection = DriverManager.getConnection(url, username, password);

        return connection;

    }

}
