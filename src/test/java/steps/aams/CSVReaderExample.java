package steps.aams;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReaderExample {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\QA02\\Downloads\\Compressed\\swl_ppp_api-performance_test SBOBET\\swl_ppp_api-performance_test\\player_accounts.csv";
        List<String[]> csvData = readDataFromCSV(filePath);

        // Iterate through the CSV data and process each row
        for (String[] row : csvData) {
            String column1 = row[0];
            // ... Process the data from each column
            System.out.println("Column 1: " + column1);
        }
    }

    public static List<String[]> readDataFromCSV(String filePath) {
        List<String[]> data = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                data.add(line);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return data;
    }
}