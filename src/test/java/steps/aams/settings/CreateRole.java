package steps.aams.settings;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pages.PageModelBase;
import pages.aams.Navigation;
import pages.aams.settings.roles.CreateRolePage;
import pages.aams.settings.roles.Roles;

import java.util.List;

public class CreateRole {
    private final WebDriver driver;
    private final Navigation navigation;
    private final Roles roles;
    private final CreateRolePage createRolePage;
    private final PageModelBase baseAction;

    public CreateRole(Driver driver) {
        this.driver = driver.get();
        this.navigation = new Navigation(this.driver);
        this.roles = new Roles(this.driver);
        this.createRolePage = new CreateRolePage(this.driver);
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
    public void fillUpFields() {
        // Input value in  role name field
        baseAction.enterValue(createRolePage.roleNameInput, "Admin Role");

        // Input value in description field
        baseAction.enterValue(createRolePage.descriptionInput, "Admin Role");

        // Select Status
        baseAction.selectDropdownOption(createRolePage.statusDropdown, "active");

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
                createRolePage.enableViewAccess(pageName);

                if (editAccessPermission.equals("no_edit")) {
                    // Disable edit access
                    createRolePage.disableEditAccess(pageName);
                }
            }
        }
    }
}
