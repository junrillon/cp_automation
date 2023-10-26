package steps.aams.report.summary;

import database.AudTransSummaryRow;
import dataRetrieval.AudTransSummaryDataRetrieval;
import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.PageModelBase;
import pages.aams.Navigation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SummaryReport {
    private final WebDriver driver;
    private final Navigation navigation;
    private final pages.aams.report.SummaryReport summaryReport;
    private final PageModelBase baseAction;
    public static List<String> websiteData = new ArrayList<>();
    public static  List<String> sqlDatabaseData = new ArrayList<>();
    public static  List<String> excelFileData = new ArrayList<>();

    public SummaryReport(Driver driver) {
        this.driver = driver.get();
        this.summaryReport = new pages.aams.report.SummaryReport(this.driver);
        this.navigation = new Navigation(this.driver);
        this.baseAction = new PageModelBase(this.driver);
    }

    @When("I navigate to summary report page")
    public void navigateToSummaryReportPage(){
        // Navigate to summary reports
        navigation.navigateToSummaryReport();
    }

    @And("filter the date {string}")
    public void filterDate(String dateFilter){
        // Enter value in the date filter
        baseAction.enterValue(summaryReport.dateFilter, dateFilter);
    }

    @And("select a courier {string}")
    public void selectCourier(String companyCode){
        // Select an option in the courier dropdown
        baseAction.selectDropdownOption(summaryReport.courierDropdown, companyCode);
    }

    @And("I click the summary filter button")
    public void clickSummaryFilterButton(){
        // Click filter button
        baseAction.clickButton(summaryReport.filterButton);
    }

    @And("I sort the data based on the date")
    public void sortData(){
        // Click filter button
        baseAction.clickButton(summaryReport.dateColumn);
    }

    @Then("I get data from the table")
    public void getDataFromTable() throws InterruptedException {
        // Wait for 2 seconds
        Thread.sleep(2000);

        // Get data from the table
        websiteData = baseAction.retrieveDataFromWebsiteTable(summaryReport.summaryTable);
        System.out.println("websiteData: " + websiteData);
    }

    @And("I get data from the database")
    public void getDataFromDatabase(DataTable reportFilters){

        //Get data table from feature file
        List<List<String>> data = reportFilters.asLists(String.class);
        String startDate = data.get(1).get(0);
        String endDate = data.get(1).get(1);
        String companyCode = data.get(1).get(2);

        // Create an instance of AudTransSummaryDataRetrieval
        AudTransSummaryDataRetrieval dataRetriever = new AudTransSummaryDataRetrieval();

        // Call the getDataFromDatabase method
        List<AudTransSummaryRow> databaseData = dataRetriever.getDataFromDatabase(startDate, endDate, companyCode);

        // Process the retrieved data
        for (AudTransSummaryRow row : databaseData) {
            // Access the properties of each row
            String date = row.getDate();
            String newTransTotal = row.getNewTransTotal();
            String completedTransTotal = row.getCompletedTransTotal();
            String exceptionTransTotal = row.getExceptionTransTotal();
            String newComplaints = row.getNewComplaints();

            // Build a string representation of the row
            String rowData = date + ", " + newTransTotal + ", " + completedTransTotal + ", " +
                    exceptionTransTotal + ", " + newComplaints;

            // Add the row data to the sqlDatabaseData list
            sqlDatabaseData.add(rowData);
        }

        System.out.println("sqlData: " + sqlDatabaseData);
    }

    @Then("I compare the website and database data")
    public void compareWebsiteAndDatabaseData(){
        // Perform assertions to compare the data
        Assert.assertEquals(websiteData, sqlDatabaseData);
    }

    /** STEPS FOR EXPORT TO EXCEL STARTS HERE **/
    @And("I click the export to excel button")
    public void clickTheExportToExcelButton(){
        // click the export to excel button
        baseAction.clickButton(summaryReport.exportToExcelButton);
    }

    @Then("summary excel file should be downloaded {string}")
    public void summaryExcelFileShouldBeDownloaded(String filePath) throws InterruptedException {
        // Set the filePath
        File downloadedFile = new File(filePath);

        // Wait for 2 seconds before checking if the file is exists
        Thread.sleep(2000);

        if (downloadedFile.exists()) {
            // Get the file name
            String fileName = downloadedFile.getName();

            // Print a message
            System.out.println("File " + fileName +" exists");

        } else {
            // Print a message
            System.out.println("File does not exists");

            // Assert if the downloaded file is existing
            // This should fail
            Assert.assertTrue(downloadedFile.exists());
        }
    }

    @Then("I get data from the downloaded file")
    public void getDataFromDownloadedFile(){
        String filePath = "C:\\Users\\QA02\\Downloads\\summary-reports.xlsx";

        // Get data from the Excel file
        excelFileData = baseAction.getDataFromExcelFile(filePath);
        System.out.println("excelFileData " + excelFileData);
    }

    @Then("website and excel data should be the same")
    public void compareWebsiteAndExcelData(){
        // Perform assertions to compare the data
        Assert.assertEquals(websiteData, excelFileData);
    }
    /** STEPS FOR EXPORT TO EXCEL ENDS HERE **/

    @Then("I should see a message in summary report")
    public void shouldSeeAMessage(String expectedMessage){
        // Get modal message and assert the message, and then close the modal
        baseAction.assertModalMessage(summaryReport.modalBody, summaryReport.modalMessage.get(0),
                summaryReport.modalCloseButton, expectedMessage);
    }
}
