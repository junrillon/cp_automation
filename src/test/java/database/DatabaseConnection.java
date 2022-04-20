package database;

import java.sql.*;

public class DatabaseConnection {
    private static Connection conn=null;



    public static ResultSet execDBQuery(String queryString) throws SQLException {
        String db = "stage_pool_betting";
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
        conn = DriverManager.getConnection("jdbc:mysql://54.65.174.214:3306/"+db+"", "qa_autobot", "eb%AqG-qvumD96Qe");
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery(queryString);
        rs.next();
        return rs;

    }

    public static void executeDBUpdate(String queryUpdate) throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }

        conn = DriverManager.getConnection("jdbc:mysql://54.65.174.214:3306/stage_pool_betting", "qa_autobot", "eb%AqG-qvumD96Qe");
        Statement stmt = conn.prepareStatement(queryUpdate, Statement.RETURN_GENERATED_KEYS);
         stmt.execute(queryUpdate);

    }
    public void closeConn() throws SQLException {
        if(conn !=null && !conn.isClosed()) {
            conn.close();
        }
        conn=null;
    }
}
