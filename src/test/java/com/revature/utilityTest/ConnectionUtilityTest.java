package com.revature.utilityTest;

import com.revature.utility.ConnectionUtility;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtilityTest {

    @Test
    public void testConnection() throws SQLException {

        Connection con = ConnectionUtility.getConnection();
        System.out.println(con);
    }

}
