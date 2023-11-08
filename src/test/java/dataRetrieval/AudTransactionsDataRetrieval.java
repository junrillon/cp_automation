package dataRetrieval;

import database.AudTransactionsRow;
import database.DatabaseConnection;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AudTransactionsDataRetrieval {
    public List<AudTransactionsRow> getDataFromDatabase(String startDate, String endDate, String companyCode) {
        List<AudTransactionsRow> databaseData = new ArrayList<>();

        try {
            String query = "SELECT a.created_at, a.trans_id, a.company_code, a.expected_delivery_date, a.delivery_date_time, a.trans_status\n" +
                    "FROM aud_transactions a \n" +
                    " INNER JOIN (\n" +
                    "   SELECT trans_id, MAX(created_at) AS latest \n" +
                    "   FROM aud_transactions \n" +
                    "    GROUP BY trans_id \n" +
                    ") b ON a.created_at = b.latest AND a.trans_id = b.trans_id \n" +
                    "WHERE a.trans_id != 0 \n" +
                    "AND created_at >= '"+ startDate +" 00:00:00' AND created_at <= '"+ endDate +" 23:59:59' \n" +
                    "AND company_code = '"+ companyCode +"' \n" +
                    "ORDER BY created_at DESC;";

            ResultSet resultSet = DatabaseConnection.executeQuery(query, "52.220.196.228:3306", "stage_aams");

            //Moving the cursor from default position to 1st row.
            resultSet.beforeFirst();

            while (resultSet.next()) {
                AudTransactionsRow row = new AudTransactionsRow();
                row.setCreatedAt(resultSet.getString("created_at"));
                row.setTransId(resultSet.getString("trans_id"));
                row.setCompanyCode(resultSet.getString("company_code"));
                row.setExpectedDeliveryDate(resultSet.getString("expected_delivery_date"));
                row.setDeliveryDateTime(resultSet.getString("delivery_date_time"));

                int transStatus = resultSet.getInt("trans_status");
                String transStatusWord;

                switch (transStatus) {
                    case 1:
                        transStatusWord = "New";
                        break;
                    case 2:
                        transStatusWord = "Pickup";
                        break;
                    case 3:
                        transStatusWord = "Sorting";
                        break;
                    case 4:
                        transStatusWord = "Delivery";
                        break;
                    case 5:
                        transStatusWord = "Completed";
                        break;
                    case 6:
                        transStatusWord = "Lost";
                        break;
                    default:
                        transStatusWord = "Unknown";
                        break;
                }

                row.setTransStatus(transStatusWord);
                databaseData.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return databaseData;
    }
}
