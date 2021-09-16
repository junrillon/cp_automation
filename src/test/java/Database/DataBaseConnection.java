package Database;

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
        conn = DriverManager.getConnection("jdbc:mysql://34.97.125.96:3306/", "prod_jerald", "f_Mf+b8gDqQ6S4Z-");
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
