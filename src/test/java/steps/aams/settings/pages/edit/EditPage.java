package steps.aams.settings.pages.edit;

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
import pages.aams.settings.pages.CreateEditPagesPage;
import pages.aams.settings.pages.Pages;

import java.util.List;

import static utilities.Tools.getRandomIntString;

public class EditPage {
    private final WebDriver driver;
    private final Pages pages;
    private final CreateEditPagesPage createEditPage;
    private final PageModelBase baseAction;
    public static String pageName;
    public static String pagePath;
    public static String status;

    public EditPage(Driver driver) {
        this.driver = driver.get();
        this.pages = new Pages(this.driver);
        this.baseAction = new PageModelBase(this.driver);
        this.createEditPage = new CreateEditPagesPage(this.driver);
    }

    @When("search for {string} to edit")
    public void searchPageToEdit(String currentPageName){
        // Enter value in search field
        baseAction.enterValue(pages.searchInput, currentPageName);

        // Click the filter button
        baseAction.clickButton(pages.filterButton);

        // Click create page button
        baseAction.clickButton(pages.editPageButton);
    }

    @When("I update the page details")
    public void setPageDetails(DataTable pageDetails){
        // Create a random an int string value to add in the page name and path
        String randomIntString = getRandomIntString(3);

        //Get data table from feature file
        List<List<String>> data = pageDetails.asLists(String.class);

        // Assign the current values to a String and concat randomIntString
        pageName = data.get(1).get(0) + " " + randomIntString;
        pagePath = data.get(1).get(1) + "-" + randomIntString;
        status = data.get(1).get(2);

        // Input value in page name input field
        baseAction.enterValue(createEditPage.pageNameInput, pageName);

        // Input value in page path input field
        baseAction.enterValue(createEditPage.pagePathInput, pagePath);

        // Select a status
        baseAction.selectDropdownOption(createEditPage.editStatusDropdown, status);
    }

    @Then("I click the submit button for page editing.")
    public void clickSubmitForPageEditing(){
        String expectedMessage = "Edit page successful.";

        // Click the submit button
        baseAction.clickButton(createEditPage.submitButtonEdit);

        // Get modal message and assert the message, and then close the modal
        baseAction.assertModalMessage(createEditPage.modalBody, createEditPage.modalMessage.get(0),
                createEditPage.modalCloseButton, expectedMessage);

        // Click the View Pages button (To go back to the Pages)
        baseAction.clickButton(createEditPage.viewPagesButton);

    }

    @Then("I check if the changes in the page were saved.")
    public void checkIfTheChangesInThePageWereSaved(){
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
