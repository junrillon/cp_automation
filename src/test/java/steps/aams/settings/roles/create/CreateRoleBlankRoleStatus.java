package steps.aams.settings.roles.create;

import engine.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pages.PageModelBase;
import pages.aams.settings.roles.CreateEditRolePage;

import java.util.List;

public class CreateRoleBlankRoleStatus {
    private final WebDriver driver;
    private final CreateEditRolePage createEditRolePage;
    private final PageModelBase baseAction;

    public static String roleName;
    public static String desc;

    public CreateRoleBlankRoleStatus(Driver driver) {
        this.driver = driver.get();
        this.createEditRolePage = new CreateEditRolePage(this.driver);
        this.baseAction = new PageModelBase(this.driver);
    }

    @Then("I leave the status dropdown blank")
    public void leaveStatusBlank(DataTable roleDetails) {
        // Get the details in the Cucumber/Feature file
        List<List<String>> data = roleDetails.asLists(String.class);
        roleName = data.get(1).get(0);
        desc = data.get(1).get(1);

        // Input value in role name field
        baseAction.enterValue(createEditRolePage.roleNameInput, roleName);

        // Select Status
        baseAction.enterValue(createEditRolePage.descriptionInput, desc);

    }

    @Then("I submit the role creation form with blank role status")
    public void submitRoleCreationWithBlankStatus(){
        // Click submit button
        baseAction.clickButton(createEditRolePage.submitButton);
    }
}
