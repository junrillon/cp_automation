package steps.aams.settings.pages.create;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.PageModelBase;
import pages.aams.Navigation;
import pages.aams.settings.pages.CreateEditPagesPage;
import pages.aams.settings.pages.Pages;

import java.util.List;

public class CreatePage {
    private final WebDriver driver;
    private final Navigation navigation;
    private final Pages pages;
    private final CreateEditPagesPage createEditPagesPage;

    private final PageModelBase baseAction;

    public static String pageName;
    public static String pagePath;
    public static String status;

    public CreatePage(Driver driver) {
        this.driver = driver.get();
        this.navigation = new Navigation(this.driver);
        this.pages = new Pages(this.driver);
        this.baseAction = new PageModelBase(this.driver);
        this.createEditPagesPage = new CreateEditPagesPage(this.driver);
    }

    @When("I navigate to pages")
    public void navigateToPages(){
        // Navigate to pages
        navigation.navigateToPages();
    }

    @When("I create a page")
    public void createPage(){
        // Click create page button
        baseAction.clickButton(pages.createPageButton);
    }

    @When("I set page details")
    public void setPageDetails(DataTable pageDetails){
        //Get data table from feature file
        List<List<String>> data = pageDetails.asLists(String.class);
        pageName = data.get(1).get(0);
        pagePath = data.get(1).get(1);
        status = data.get(1).get(2);

        // Input value in page name input field
        baseAction.enterValue(createEditPagesPage.pageNameInput, pageName);

        // Input value in page path input field
        baseAction.enterValue(createEditPagesPage.pagePathInput, pagePath);

        // Select a status
        baseAction.selectDropdownOption(createEditPagesPage.statusDropdown, status);
    }

    @Then("I click submit button for page creation")
    public void clickSubmitForPageCreation(){
        // Click the submit button
        baseAction.clickButton(createEditPagesPage.submitButtonCreate);

        // Click the View Pages button (To go back to the Pages)
        baseAction.clickButton(createEditPagesPage.viewPagesButton);

    }

    @Then("I check if page is created")
    public void checkCreatedPage(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        // Input value in page name input field
        baseAction.enterValue(pages.searchInput, pageName);

        // Click the filter button
        baseAction.clickButton(pages.filterButton);

        // Declare table element and wait for it to be visible.
        WebElement table = wait.until(ExpectedConditions.visibilityOf(pages.table));
        int cellCount = baseAction.getCellCount(table);

        if (cellCount > 1) {
            //Get the value of the cell
            String pageNameCell = baseAction.getCellValue(table, 0);
            String pagePathCell = baseAction.getCellValue(table, 1);
            String pageStatusCell = baseAction.getCellValue(table, 2);

            // Assert the value of the cell
            Assert.assertEquals(pageNameCell, pageName);
            Assert.assertEquals(pagePathCell, pagePath);
            Assert.assertTrue(pageStatusCell.equalsIgnoreCase(status));

        } else {
            //Get the value of the cell and assert
            Assert.assertEquals(baseAction.getCellValue(table, 0), pageName);
        }
    }
}
