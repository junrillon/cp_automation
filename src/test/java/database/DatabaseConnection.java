package database;

import java.sql.*;

public class DatabaseConnection {
    private static Connection conn=null;

    public static ResultSet execDBQuery(String queryString) throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
        conn = DriverManager.getConnection("jdbc:mysql://54.65.174.214:3306/stage_pool_betting", "stage_jun", "#3s9VrD$P!Gg-cda");
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery(queryString);
        rs.next();
        return rs;

        //54.65.174.214 <- b2c staging "stage_jun", "#3s9VrD$P!Gg-cda"
        //18.140.122.219:3306 <- dev pool bet "stage.jun.rillon", "hEUN5dI8UN5dI894s");
    }
    public void closeConn() throws SQLException {
        if(conn !=null && !conn.isClosed()) {
            conn.close();
        }
        conn=null;
    }
}
