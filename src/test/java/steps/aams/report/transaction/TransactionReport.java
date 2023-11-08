package steps.aams.report.transaction;

import dataRetrieval.AudTransactionsDataRetrieval;
import database.AudTransactionsRow;
import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.PageModelBase;
import pages.aams.Navigation;
import pages.aams.report.TransactionReportPage;

import java.util.ArrayList;
import java.util.List;

public class TransactionReport {
    private final WebDriver driver;
    private final Navigation navigation;
    private final PageModelBase baseAction;
    private final TransactionReportPage transactionReportPage;

    public static List<String> websiteData = new ArrayList<>();
    public static  List<String> sqlDatabaseData = new ArrayList<>();
    public static  List<String> excelFileData = new ArrayList<>();

    public TransactionReport(Driver driver) {
        this.driver = driver.get();
        this.navigation = new Navigation(this.driver);
        this.baseAction = new PageModelBase(this.driver);
        this.transactionReportPage = new TransactionReportPage(this.driver);
    }

    @When("I navigate to transaction report page")
    public void navigateToTransactionReportPage(){
        // Navigate to summary reports
        navigation.navigateToTransactionReport();
    }

    @And("filter the date {string} in transaction report")
    public void filterDate(String dateFilter){
        // Enter value in the date filter
        baseAction.enterValue(transactionReportPage.dateFilter, dateFilter);
    }

    @And("select a courier {string} in transaction report")
    public void selectCourier(String companyCode){
        // Select an option in the courier dropdown
        baseAction.selectDropdownOption(transactionReportPage.courierDropdown, companyCode);
    }

    @And("I click the transaction filter button")
    public void clickTransactionFilterButton(){
        // Click filter button
        baseAction.clickButton(transactionReportPage.filterButton);
    }

    @Then("I will get data from the transaction datatable")
    public void getDataFromTable() throws InterruptedException {
        // Wait for 2 seconds
        Thread.sleep(2000);

        // Get data from the table
        websiteData = baseAction.retrieveDataFromWebsiteTable(transactionReportPage.transactionTable);
        System.out.println("websiteData: \n     " + websiteData);
    }

    @And("get data from the aud_transactions")
    public void getDataFromDatabase(DataTable reportFilters){

        //Get data table from feature file
        List<List<String>> data = reportFilters.asLists(String.class);
        String startDate = data.get(1).get(0);
        String endDate = data.get(1).get(1);
        String companyCode = data.get(1).get(2);

        // Create an instance of AudTransSummaryDataRetrieval
        AudTransactionsDataRetrieval dataRetriever = new AudTransactionsDataRetrieval();

        // Call the getDataFromDatabase method
        List<AudTransactionsRow> databaseData = dataRetriever.getDataFromDatabase(startDate, endDate, companyCode);

        // Process the retrieved data
        for (AudTransactionsRow row : databaseData) {
            // Access the properties of each row
            String createdAt = row.getCreatedAt();
            String transId = row.getTransId();
            String companyCode1 = row.getCompanyCode();
            String expectedDeliveryDate = row.getExpectedDeliveryDate();
            String deliveryDateTime = row.getDeliveryDateTime();
            String transStatus = row.getTransStatus();

            // Build a string representation of the row
            String rowData = createdAt + ", " + transId + ", " + companyCode1 + ", " +
                    expectedDeliveryDate + ", " + deliveryDateTime + ", " + transStatus;

            // Add the row data to the sqlDatabaseData list
            sqlDatabaseData.add(rowData);
        }

        System.out.println("sqlData: \n     " + sqlDatabaseData);
    }

    @Then("data from transaction page and aud_transactions table should be the same")
    public void compareWebsiteAndDatabaseData(){
        // Perform assertions to compare the data
        if (websiteData.equals(sqlDatabaseData)) {
            System.out.println("Data is equal. The website data matches the SQL database data.");
        } else {
            Assert.assertEquals(websiteData, sqlDatabaseData);
        }
    }
}
