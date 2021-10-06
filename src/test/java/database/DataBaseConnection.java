package database;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Connection;

public class DataBaseConnection {

    private static Connection conn=null;

    public static ResultSet execDBQuery(String queryString) throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
        conn = DriverManager.getConnection("jdbc:mysql://18.142.94.161:3306/prod_pool_betting", "prod.jun.rillon", "hEUN5dI8UN5dI894s");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(queryString);
        rs.next();
        return rs;
    }
    public void closeConn() throws SQLException {
        if(conn !=null && !conn.isClosed()) {
            conn.close();
        }
        conn=null;
    }
}
