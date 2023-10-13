package steps.aams.settings.roles.create;

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
import pages.aams.settings.roles.CreateEditRolePage;
import pages.aams.settings.roles.Roles;

import java.util.List;

public class CreateRole {
    private final WebDriver driver;
    private final Navigation navigation;
    private final Roles roles;
    private final CreateEditRolePage createEditRolePage;
    private final PageModelBase baseAction;

    private static String roleName;
    public static String desc;
    public static String status;

    public CreateRole(Driver driver) {
        this.driver = driver.get();
        this.navigation = new Navigation(this.driver);
        this.roles = new Roles(this.driver);
        this.createEditRolePage = new CreateEditRolePage(this.driver);
        this.baseAction = new PageModelBase(this.driver);
    }

    @When("I navigate to roles")
    public void navigateToRoles() {
        // Navigate to pages
        navigation.navigateToRoles();
    }

    @When("click create role button")
    public void createPage() {
        // Click create page button
        baseAction.clickButton(roles.createRoleButton);

    }

    @Then("fill up the necessary fields")
    public void fillUpFields(DataTable roleDetails) {
        // Get the details in the Cucumber/Feature file
        List<List<String>> data = roleDetails.asLists(String.class);
        roleName = data.get(1).get(0);
        desc = data.get(1).get(1);
        status = data.get(1).get(2);

        // Input value in  role name field
        baseAction.enterValue(createEditRolePage.roleNameInput, roleName);

        // Input value in description field
        baseAction.enterValue(createEditRolePage.descriptionInput, desc);

        // Select Status
        baseAction.selectDropdownOption(createEditRolePage.statusDropdown, status);

    }

    @Then("adjust permission per page")
    public void adjustPermissionPerPage(DataTable location) {
        // Get the location of the CSV file.
        List<List<String>> data = location.asLists(String.class);
        String filePath = data.get(1).get(0);

        // Read data from the CSV file
        List<String[]> csvRows = baseAction.readDataFromCSV(filePath);

        // Iterate through the CSV data and process each row
        for (String[] row : csvRows) {
            String pageName = row[0];
            String viewAccessPermission = row[1];
            String editAccessPermission = row[2];

            if (viewAccessPermission.equals("with_view")) {
                // Enable view access
                createEditRolePage.enableViewAccess(pageName);

                if (editAccessPermission.equals("no_edit")) {
                    // Disable edit access
                    createEditRolePage.disableEditAccess(pageName);
                }
            }
        }
    }

    @Then("I click the submit button for role creation")
    public void clickSubmitButtonForRoleCreation(){
        String expectedMessage = "Create role successful.";

        // Scroll to top of the webpage
        baseAction.scrollToTop();

        // Wait for the View Roles button to be visible
        baseAction.waitForVisibility(createEditRolePage.viewRolesButton, 20);

        // Click submit button
        baseAction.clickButton(createEditRolePage.submitButton);

        // Get modal message and assert the message, and then close the modal
        baseAction.assertModalMessage(createEditRolePage.modalBody, createEditRolePage.modalMessage.get(0),
                                        createEditRolePage.modalCloseButton, expectedMessage);

        // Click the View Roles button (To go back to the Roles Page)
        baseAction.clickButton(createEditRolePage.viewRolesButton);
    }

    @Then("I check if role is created")
    public void checkCreatedRole(){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        // Input value in page name input field
        baseAction.selectDropdownOption(roles.roleDropdown, roleName);

        // Click the filter button
        baseAction.clickButton(roles.filterButton);

        // Declare table element and wait for it to be visible.
        WebElement table = wait.until(ExpectedConditions.visibilityOf(roles.table));
        int cellCount = baseAction.getCellCount(table);
        String roleNameCell;

        if (cellCount > 1) {
            String roleDescCell = baseAction.getCellValue(table, 1);
            String roleStatusCell = baseAction.getCellValue(table, 2);

            Assert.assertEquals(roleNameCell = baseAction.getCellValue(table, 0), roleName,
                    "Expected Role Name: " + roleName + ", Actual Role Name: " + roleNameCell);

            Assert.assertEquals(roleDescCell, desc,
                    "Expected Description: " + desc + ", Actual Description: " + roleDescCell);

            Assert.assertTrue(roleStatusCell.equalsIgnoreCase(status),
                    "Expected Role Status: " + status + ", Actual Role Status: " + roleStatusCell);
        } else {
            //Get the value of the cell and assert
            Assert.assertEquals(baseAction.getCellValue(table, 0), roleName,
                    "The " + roleName + " is not found.");
        }
    }
}
