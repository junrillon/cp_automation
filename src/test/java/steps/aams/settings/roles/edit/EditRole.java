package steps.aams.settings.roles.edit;

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
import pages.aams.settings.roles.CreateEditRolePage;
import pages.aams.settings.roles.Roles;

import java.util.List;

import static utilities.Tools.getRandomIntString;

public class EditRole {
    private final WebDriver driver;
    private final Roles roles;
    private final CreateEditRolePage createEditRolePage;
    private final PageModelBase baseAction;

    public static String roleName;
    public static String desc;
    public static String status;

    public EditRole(Driver driver) {
        this.driver = driver.get();
        this.roles = new Roles(this.driver);
        this.baseAction = new PageModelBase(this.driver);
        this.createEditRolePage = new CreateEditRolePage(this.driver);
    }

    @When("search for {string} role to edit")
    public void searchRoleToEdit(String currentRoleName){
        // Select role in the dropdown
        baseAction.selectDropdownOption(roles.roleDropdown, currentRoleName);

        // Click the filter button
        baseAction.clickButton(roles.filterButton);

        // Click edit button
        baseAction.clickButton(roles.editRoleButton);
    }

    @When("I update the role details in edit page")
    public void setRoleDetailsInEditPage(DataTable roleDetails){
        // Create a random an int string value to add in the page name and path
        String randomIntString = getRandomIntString(3);

        //Get data table from feature file
        List<List<String>> data = roleDetails.asLists(String.class);

        // Assign the current values to a String and concat randomIntString
        roleName = data.get(1).get(0) + " " + randomIntString;
        desc = data.get(1).get(1) + " " + randomIntString;
        status = data.get(1).get(2);

        // Input value in page name input field
        baseAction.enterValue(createEditRolePage.roleNameInput, roleName);

        // Input value in page path input field
        baseAction.enterValue(createEditRolePage.descriptionInput, desc);

        // Select a status
        baseAction.selectDropdownOption(createEditRolePage.statusDropdown, status);
    }

    @Then("I submit the complete role editing form")
    public void submitCompleteRoleEditingForm(){
        String expectedMessage = "Edit role successful.";

        // Click the submit button
        baseAction.clickButton(createEditRolePage.submitButtonEdit);

        // Get modal message and assert the message, and then close the modal
        baseAction.assertModalMessage(createEditRolePage.modalBody, createEditRolePage.modalMessage.get(0),
                createEditRolePage.modalCloseButton, expectedMessage);

        // Click the View Pages button (To go back to the Pages)
        baseAction.clickButton(createEditRolePage.viewRolesButton);

    }

    @Then("the changes should be saved")
    public void changesShouldBeSaved(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        // Select role in the dropdown
        baseAction.selectDropdownOption(roles.roleDropdown, roleName);

        // Click the filter button
        baseAction.clickButton(roles.filterButton);

        // Declare table element and wait for it to be visible.
        WebElement table = wait.until(ExpectedConditions.visibilityOf(roles.table));
        int cellCount = baseAction.getCellCount(table);

        if (cellCount > 1) {
            //Get the value of the cell
            String roleNameCell = baseAction.getCellValue(table, 0);
            String roleDescCell = baseAction.getCellValue(table, 1);
            String roleStatusCell = baseAction.getCellValue(table, 2);

            // Assert the value of the cell
            Assert.assertEquals(roleNameCell, roleName);
            Assert.assertEquals(roleDescCell, desc);
            Assert.assertTrue(roleStatusCell.equalsIgnoreCase(status));

        } else {
            //Get the value of the cell and assert
            Assert.assertEquals(baseAction.getCellValue(table, 0), roleName);
        }
    }
}
