package steps.aams.report.transaction;

import engine.Driver;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pages.aams.Navigation;

public class TransactionReport {
    private final WebDriver driver;
    private final Navigation navigation;

    public TransactionReport(Driver driver) {
        this.driver = driver.get();
        this.navigation = new Navigation(this.driver);
    }

    @When("I navigate to transaction report page")
    public void navigateToTransactionReportPage(){
        // Navigate to summary reports
        navigation.navigateToTransactionReport();
    }
}
