package steps.aams.settings.roles.create;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pages.PageModelBase;
import pages.aams.settings.roles.CreateEditRolePage;

import java.util.List;

public class CreateRoleBlankRoleDesc {
    private final WebDriver driver;
    private final CreateEditRolePage createEditRolePage;
    private final PageModelBase baseAction;

    public static String roleName;
    public static String status;

    public CreateRoleBlankRoleDesc(Driver driver) {
        this.driver = driver.get();
        this.createEditRolePage = new CreateEditRolePage(this.driver);
        this.baseAction = new PageModelBase(this.driver);
    }

    @Then("I leave the description field blank")
    public void leaveDescriptionBlank(DataTable roleDetails) {
        // Get the details in the Cucumber/Feature file
        List<List<String>> data = roleDetails.asLists(String.class);
        roleName = data.get(1).get(0);
        status = data.get(1).get(1);

        // Input value in description field
        baseAction.enterValue(createEditRolePage.roleNameInput, roleName);

        // Select Status
        baseAction.selectDropdownOption(createEditRolePage.statusDropdown, status);

    }
    @Then("I submit the role creation form with blank role desc")
    public void submitRoleCreationWithBlankDesc(){
        // Click submit button
        baseAction.clickButton(createEditRolePage.submitButton);
    }
}
