package dataRetrieval;

import database.AudTransSummaryRow;
import database.DatabaseConnection;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AudTransSummaryDataRetrieval {
    public List<AudTransSummaryRow> getDataFromDatabase(String startDate, String endDate, String companyCode) {
        List<AudTransSummaryRow> databaseData = new ArrayList<>();

        try {
            String query = "SELECT DATE, new_trans_total, completed_trans_total, exception_trans_total, new_complaints FROM aud_trans_summary " +
                    "WHERE DATE BETWEEN '" + startDate + "' AND '" + endDate + "' AND company_code = '" + companyCode + "' ORDER BY DATE ASC;";

            ResultSet resultSet = DatabaseConnection.executeQuery(query, "52.220.196.228:3306", "stage_aams");

            //Moving the cursor from default position to 1st row.
            resultSet.beforeFirst();

            while (resultSet.next()) {
                AudTransSummaryRow row = new AudTransSummaryRow();
                row.setDate(resultSet.getString("date"));
                row.setNewTransTotal(resultSet.getString("new_trans_total"));
                row.setCompletedTransTotal(resultSet.getString("completed_trans_total"));
                row.setExceptionTransTotal(resultSet.getString("exception_trans_total"));
                row.setNewComplaints(resultSet.getString("new_complaints"));

                databaseData.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return databaseData;
    }
}
